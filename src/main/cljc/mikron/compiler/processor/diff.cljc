(ns mikron.compiler.processor.diff
  "Differ and undiffer generating functions."
  (:require [mikron.compiler.processor.common :as common]
            [mikron.compiler.schema :as compiler.schema]
            [mikron.compiler.util :as compiler.util]
            ;; Runtime
            [mikron.runtime.processor.common :as runtime.processor.common]))

(def hierarchy
  "The default schema hierarchy + groupings for comparability."
  (-> compiler.schema/hierarchy
      (compiler.schema/derive-all :identical?-comparable [:boolean :nil])
      (compiler.schema/derive-all :=-comparable          [:char :string :symbol :any])
      (compiler.schema/derive-all :keyword-comparable    [:keyword :enum])))

(defmulti diff
  "Returns the generated (un)differ code for a given schema."
  compiler.schema/schema-name
  :hierarchy #'hierarchy)

(defn diff*
  "Returns the generated (un)differ code for a given schema."
  [schema paths value-1 value-2 {:keys [processor-type] :as opts}]
  (if-not paths
    (diff [:default] paths value-1 value-2 opts)
    (case processor-type
      :diff   (diff schema paths value-1 value-2 opts)
      :undiff `(if (runtime.processor.common/keyword-identical? :mikron/dnil ~value-2)
                 ~value-1
                 ~(diff schema paths value-1 value-2 opts)))))

(defmethod diff :number [_ _ value-1 value-2 {:keys [processor-type]}]
  (case processor-type
    :diff   `(if (== ~value-1 ~value-2)
               :mikron/dnil
               ~value-2)
    :undiff value-2))

(defmethod diff :=-comparable [_ _ value-1 value-2 {:keys [processor-type]}]
  (case processor-type
    :diff   `(if (= ~value-1 ~value-2)
               :mikron/dnil
               ~value-2)
    :undiff value-2))

(defmethod diff :identical?-comparable [_ _ value-1 value-2 {:keys [processor-type]}]
  (case processor-type
    :diff   `(if (identical? ~value-1 ~value-2)
               :mikron/dnil
               ~value-2)
    :undiff value-2))

(defmethod diff :keyword-comparable [_ _ value-1 value-2 {:keys [processor-type]}]
  (case processor-type
    :diff   `(if (runtime.processor.common/keyword-identical? ~value-1 ~value-2)
               :mikron/dnil
               ~value-2)
    :undiff value-2))

(defmethod diff :optional [[_ _ schema'] paths value-1 value-2 opts]
  `(if (and ~value-1 ~value-2)
     ~(diff schema' paths value-1 value-2 opts)
     ~(diff [:default] nil value-1 value-2 opts)))

(defmethod diff :multi [[_ _ selector schemas'] paths value-1 value-2 opts]
  (compiler.util/with-gensyms [case-1 case-2]
    (if-not paths
      (diff [:default] nil value-1 value-2 opts)
      `(let [~case-1 (~selector ~value-1)
             ~case-2 (~selector ~value-2)]
         (if (not= ~case-1 ~case-2)
           ~(diff [:default] nil value-1 value-2 opts)
           (condp = ~case-1
             ~@(mapcat (fn [[key' schema']]
                         [key' (if-let [paths' (paths key')]
                                 (diff schema' paths' value-1 value-2 opts)
                                 (diff [:default] nil value-1 value-2 opts))])
                       schemas')))))))

(defmethod diff :list [[_ options schema'] paths value-1 value-2 opts]
  (compiler.util/with-gensyms [value-1-vec value-2-vec]
    `(let [~value-1-vec (vec ~value-1)
           ~value-2-vec (vec ~value-2)]
       ~(diff [:vector options schema'] paths value-1-vec value-2-vec opts))))

(defmethod diff :vector [[_ _ schema'] paths value-1 value-2 opts]
  (compiler.util/with-gensyms [index value-1' value-2' value value' length-1 length-2 same-length? all-dnil?]
    (let [paths' (:all paths)]
      (if-not paths'
        (diff [:default] nil value-1 value-2 opts)
        `(let [~length-1     (runtime.processor.common/count ~value-1)
               ~length-2     (runtime.processor.common/count ~value-2)
               ~same-length? (== ~length-1 ~length-2)]
           (loop [~value     (transient [])
                  ~index     (long 0)
                  ~all-dnil? true]
             (if (== ~index ~length-2)
               (if (and ~all-dnil? ~same-length?)
                 :mikron/dnil
                 (persistent! ~value))
               (let [~value-2' (runtime.processor.common/nth ~value-2 ~index)
                     ~value'   (if (<= ~length-1 ~index)
                                 ~(diff [:default] nil nil value-2' opts)
                                 (let [~value-1' (runtime.processor.common/nth ~value-1 ~index)]
                                   ~(diff* schema' paths' value-1' value-2' opts)))]
                 (recur (conj! ~value ~value')
                        (unchecked-inc ~index)
                        (and ~all-dnil? (identical? :mikron/dnil ~value')))))))))))

(defmethod diff :map [[_ {:keys [sorted-by]} _ val-schema] paths value-1 value-2 opts]
  (compiler.util/with-gensyms [value-1' value-2' entry-1 key-2 keys-2 value value'
                               length-1 length-2 same-length? all-dnil?]
    (let [paths' (:all paths)]
      (if-not paths'
        (diff [:default] nil value-1 value-2 opts)
        `(let [~length-1     (runtime.processor.common/count ~value-1)
               ~length-2     (runtime.processor.common/count ~value-2)
               ~same-length? (== ~length-1 ~length-2)]
           (loop [~value            ~(if sorted-by `(sorted-map-by ~sorted-by) `(transient {}))
                  [~key-2 & ~keys-2] (keys ~value-2)
                  ~all-dnil?         true]
             (if-not ~key-2
               (if (and ~all-dnil? ~same-length?)
                 :mikron/dnil
                 ~(if sorted-by value `(persistent! ~value)))
               (let [~value-2' (~value-2 ~key-2)
                     ~entry-1  (find ~value-1 ~key-2)
                     ~value'   (if ~entry-1
                                 (let [~value-1' (val ~entry-1)]
                                   ~(diff* val-schema paths' value-1' value-2' opts))
                                 ~(diff [:default] nil nil value-2' opts))]
                 (recur (~(if sorted-by `assoc `assoc!) ~value ~key-2 ~value')
                        ~keys-2
                        (and ~all-dnil? ~entry-1 (identical? :mikron/dnil ~value')))))))))))

(defmethod diff :tuple [[_ _ schemas] paths value-1 value-2 opts]
  (compiler.util/with-gensyms [value-1' value-2']
    (if-not paths
      (diff [:default] nil value-1 value-2 opts)
      (let [fields (common/tuple->fields schemas)]
        `(let [~@(mapcat (fn [[key value']]
                           [value' `(let [~value-1' ~(common/tuple-lookup value-1 key)
                                          ~value-2' ~(common/tuple-lookup value-2 key)]
                                      ~(if-let [paths' (paths key)]
                                         (diff* (schemas key) paths' value-1' value-2' opts)
                                         (diff [:default] nil value-1' value-2' opts)))])
                         fields)]
           (if (and ~@(map (fn [[_ value']]
                             `(identical? :mikron/dnil ~value'))
                           fields))
             :mikron/dnil
             ~(common/fields->tuple fields)))))))

(defmethod diff :record [[_ {:keys [type]} schemas] paths value-1 value-2 opts]
  (compiler.util/with-gensyms [value-1' value-2']
    (if-not paths
      (diff [:default] nil value-1 value-2 opts)
      (let [fields (common/record->fields schemas)]
        `(let [~@(mapcat (fn [[key value']]
                           [value' `(let [~value-1' ~(common/record-lookup value-1 key type)
                                          ~value-2' ~(common/record-lookup value-2 key type)]
                                      ~(if-let [paths' (paths key)]
                                         (diff* (schemas key) paths' value-1' value-2' opts)
                                         (diff [:default] nil value-1' value-2' opts)))])
                         fields)]
           (if (and ~@(map (fn [[_ value']]
                             `(identical? :mikron/dnil ~value'))
                           fields))
             :mikron/dnil
             ~(common/fields->record fields type)))))))

(defmethod diff :custom [schema _ value-1 value-2 {:keys [processor-type]}]
  `((deref ~(common/processor-name processor-type schema)) ~value-1 ~value-2))

(defmethod diff :default [_ _ _ value-2 _]
  value-2)

(defmethod common/processor :diff [_ {:keys [schema diff-paths] :as opts}]
  (compiler.util/with-gensyms [_ value-1 value-2]
    `([~value-1 ~value-2]
      ~(diff* schema diff-paths value-1 value-2 (assoc opts :processor-type :diff)))))

(defmethod common/processor :undiff [_ {:keys [schema diff-paths] :as opts}]
  (compiler.util/with-gensyms [_ value-1 value-2]
    `([~value-1 ~value-2]
      ~(diff* schema diff-paths value-1 value-2 (assoc opts :processor-type :undiff)))))