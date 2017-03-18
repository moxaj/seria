(ns mikron.compiler.processor.interp
  "Linear interpolator generating functions."
  (:require [mikron.compiler.schema :as compiler.schema]
            [mikron.compiler.util :as compiler.util]
            ;; Runtime
            [mikron.util.coll :as util.coll]
            [mikron.util.math :as util.math]))

(defmulti interp compiler.schema/schema-name :hierarchy #'compiler.schema/hierarchy)

(defmethod interp :char [_ _ value-1 value-2 env]
  (interp [:default] nil value-1 value-2 char))

(defmethod interp :integer [_ _ value-1 value-2 env]
  `(util.math/round ~(interp [:floating] nil value-1 value-2 env)))

(defmethod interp :floating [_ _ value-1 value-2 {:keys [time-factor]}]
  `(util.math/interp ~value-1 ~value-2 ~time-factor))

(defmethod interp :list [[_ options schema'] path value-1 value-2 env]
  (compiler.util/with-gensyms [value-1-vec value-2-vec]
    (if-not (:all path)
      (interp [:default] nil value-1 value-2 env)
      `(let [~value-1-vec (vec ~value-1)
             ~value-2-vec (vec ~value-2)]
         ~(interp [:vector options schema'] path value-1-vec value-2-vec env)))))

(defmethod interp :vector [[_ _ schema'] path value-1 value-2 env]
  (compiler.util/with-gensyms [index value-1' value-2' value value' length-1 length-2]
    (let [path' (:all path)]
      (if-not path'
        (interp [:default] nil value-1 value-2 env)
        `(let [~length-1 (util.coll/count ~value-1)
               ~length-2 (util.coll/count ~value-2)]
           (loop [~value (transient [])
                  ~index (long 0)]
             (if (== ~index ~length-2)
               (persistent! ~value)
               (let [~value-2' (util.coll/nth ~value-2 ~index)
                     ~value'   (if (<= ~length-1 ~index)
                                 ~(interp [:default] nil nil value-2' env)
                                 (let [~value-1' (util.coll/nth ~value-1 ~index)]
                                   ~(interp schema' path' value-1' value-2' env)))]
                 (recur (conj! ~value ~value')
                        (unchecked-inc ~index))))))))))

(defmethod interp :map [[_ {:keys [sorted-by]} key-schema val-schema] path value-1 value-2 env]
  (compiler.util/with-gensyms [value-1' value-2' key-2 keys-2 value value']
    (let [path' (:all path)]
      (if-not path'
        (interp [:default] nil value-1 value-2 env)
        `(loop [~value            ~(if sorted-by
                                     `(sorted-map-by ~sorted-by)
                                     `(transient {}))
                [~key-2 & ~keys-2] (keys ~value-2)]
           (if-not ~key-2
             ~(if sorted-by
                value
                `(persistent! ~value))
             (let [~value-2' (~value-2 ~key-2)
                   ~value'   (if-let [~value-1' (~value-1 ~key-2)]
                               ~(interp val-schema path' value-1' value-2' env)
                               ~(interp [:default] nil nil value-2' env))]
               (recur (~(if sorted-by `assoc `assoc!) ~value ~key-2 ~value')
                      ~keys-2))))))))

(defmethod interp :tuple [[_ _ schemas] path value-1 value-2 env]
  (compiler.util/with-gensyms [value-1' value-2']
    (if-not path
      (interp [:default] nil value-1 value-2 env)
      (let [fields (compiler.util/tuple->fields schemas)]
        `(let [~@(mapcat (fn [[key value']]
                           [value' `(let [~value-1' ~(compiler.util/tuple-lookup value-1 key)
                                          ~value-2' ~(compiler.util/tuple-lookup value-2 key)]
                                      ~(if-let [path' (path key)]
                                         (interp (schemas key) path' value-1' value-2' env)
                                         (interp [:default] nil value-1' value-2' env)))])
                         fields)]
           ~(compiler.util/fields->tuple fields))))))

(defmethod interp :record [[_ {:keys [type]} schemas] path value-1 value-2 env]
  (compiler.util/with-gensyms [value-1' value-2']
    (if-not path
      (interp [:default] nil value-1 value-2 env)
      (let [fields (compiler.util/record->fields schemas)]
        `(let [~@(mapcat (fn [[key value']]
                           [value' `(let [~value-1' ~(compiler.util/record-lookup value-1 key type)
                                          ~value-2' ~(compiler.util/record-lookup value-2 key type)]
                                      ~(if-let [path' (path key)]
                                         (interp (schemas key) path' value-1' value-2' env)
                                         (interp [:default] nil value-1' value-2' env)))])
                         fields)]
           ~(compiler.util/fields->record fields type))))))

(defmethod interp :optional [[_ _ schema'] path value-1 value-2 env]
  `(if (and ~value-1 ~value-2)
     ~(interp schema' path value-1 value-2 env)
     ~(interp [:default] nil value-1 value-2 env)))

(defmethod interp :multi [[_ _ selector schemas'] path value-1 value-2 env]
  (compiler.util/with-gensyms [case-1 case-2]
    (if-not path
      (interp [:default] nil value-1 value-2 env)
      `(let [~case-1 (~selector ~value-1)
             ~case-2 (~selector ~value-2)]
         (if-not (= ~case-1 ~case-2)
           ~(interp [:default] nil value-1 value-2 env)
           (case ~case-1
             ~@(mapcat (fn [[key' schema']]
                         [key' (if-let [path' (path key')]
                                 (interp schema' path' value-1 value-2 env)
                                 (interp [:default] nil value-1 value-2 env))])
                       schemas')))))))

(defmethod interp :wrapped [[_ _ pre post schema'] path value-1 value-2 env]
  (compiler.util/with-gensyms [value-1' value-2']
    `(let [~value-1' (~pre ~value-1)
           ~value-2' (~pre ~value-2)]
       (~post ~(interp schema' path value-1' value-2' env)))))

(defmethod interp :aliased [[schema-name] path value-1 value-2 env]
  (interp (compiler.schema/aliased-schemas schema-name) path value-1 value-2 env))

(defmethod interp :custom [schema _ value-1 value-2 {:keys [prefer-first? time-factor]}]
  `((deref ~(compiler.util/processor-name :interp schema)) ~value-1 ~value-2 ~prefer-first? ~time-factor))

(defmethod interp :default [_ _ value-1 value-2 {:keys [prefer-first?]}]
  `(if ~prefer-first? ~value-1 ~value-2))

(defmethod compiler.util/processor :interp [_ {:keys [schema ext] :as env}]
  (compiler.util/with-gensyms [_ value-1 value-2 prefer-first? time-factor]
    `([~value-1 ~value-2 ~prefer-first? ~time-factor]
      ~(interp schema (:interp ext) value-1 value-2
               (assoc env :prefer-first? prefer-first? :time-factor time-factor)))))