(ns mikron.compile-util
  "Compile time utility functions."
  #?(:cljs (:require-macros [mikron.compile-util])))

;; symbol

(def processor-name
  "Returns a memoized processor name."
  (memoize
    (fn [processor-type schema-name]
      (-> (str (name processor-type) "-" (name schema-name))
          (gensym)
          (with-meta {:processor-type processor-type
                      :schema-name    schema-name})))))

;; macro helper

(defmacro cljs?
  "Returns `true` if compiled for cljs, `false` otherwise."
  []
  `(boolean (:ns ~'&env)))

(defmacro with-gensyms
  "Executes each expression of `body` in the context of each symbol in `syms`
  bound to a generated symbol."
  [syms & body]
  `(let [~@(mapcat (fn [sym]
                     [sym `(with-meta (gensym ~(str sym)) ~(meta sym))])
                   syms)]
     ~@body))

(defmacro with-evaluated
  "Executes each expression of `body` in the context of each symbol in `syms`
  bound to an **evaluated** value. Can be used to prevent accidental multiple evaluation
  in macros."
  [syms & body]
  (let [m (into {} (map (juxt identity gensym) syms))]
    `(let [~@(mapcat (fn [[sym temp-sym]]
                       [temp-sym `(gensym '~sym)])
                     m)]
       `(let [~~@(mapcat reverse m)]
          ~(let [~@(mapcat identity m)]
             ~@body)))))

(defmacro definterface+
  "Expands to a `definterface` call in clj, `defprotocol` call in cljs."
  [name & ops]
  (let [no-meta    #(with-meta % nil)
        cljs?      (boolean (:ns &env))
        ops        (map (fn [[op-name args doc-string]]
                          [op-name
                           args
                           (vec (cons 'this (map no-meta args)))
                           (when doc-string [doc-string])])
                        ops)
        inner-form `(~(if cljs? `defprotocol `definterface)
                     ~name
                     ~@(map (fn [[op-name args args' doc-string]]
                              (if cljs?
                                `(~(no-meta op-name)
                                  ~args'
                                  ~@doc-string)
                                `(~(with-meta (munge op-name) (meta op-name))
                                  ~args
                                  ~@doc-string)))
                            ops))]
    (if cljs?
      inner-form
      `(do ~inner-form
           ~@(map (fn [[op-name args args' doc-string]]
                    `(defn ~(no-meta op-name)
                       {:inline (fn ~args'
                                  `(~'~(symbol (str "." (munge op-name)))
                                    ~~@args'))}
                       ~(with-meta (vec (cons (with-meta 'this {:tag name})
                                              args))
                                   (meta op-name))
                       (~(symbol (str "." (munge op-name)))
                        ~@args')))
                  ops)))))

;; coll

(defn find-by*
  "Walks `form` and collects all values for which the predicate `f` returns `true`.
  Does not filter duplicates."
  [f form]
  (cond-> (if (seqable? form)
            (mapcat (partial find-by* f) form)
            [])
    (f form) (conj form)))

(defn find-by
  "Walks `form` and collects all values for which the predicate `f` returns true.
  Filter duplicates."
  [f form]
  (set (find-by* f form)))

;; schema

(defn type-of
  "Returns the type of `schema` or `nil` if the schema is invalid."
  [schema & _]
  (cond
    (keyword? schema) schema
    (vector? schema)  (first schema)
    (symbol? schema)  :custom
    :else             nil))

(defn integer-type
  "Returns an integer type into which `size` can fit."
  [^long size]
  (condp > size
    256        [:byte]
    65536      [:short]
    2147483648 [:int]
    [:long]))

(defn record-lookup
  "Generates code for record value lookup."
  [record key [class]]
  (if-not class
    `(~record ~key)
    `(~(symbol (str ".-" (name key)))
      ~(with-meta record {:tag class}))))

(defn record->fields
  "Returns a map from record keys to generated symbols."
  [schemas]
  (->> (keys schemas)
       (map (fn [key]
              [key (gensym key)]))
       (into (sorted-map))))

(defn fields->record
  "Generates code which reconstructs a record from its fields."
  [fields [class & members]]
  (if-not class
    fields
    `(~(symbol (str "->" class))
      ~@(map (fn [member]
               (or (fields (keyword member)) 0))
             members))))

(defn tuple-lookup
  "Generates code for tuple value lookup."
  [tuple index]
  `(mikron.util.coll/nth ~tuple ~index))

(defn tuple->fields
  "Returns a map from tuple indices to generated symbols."
  [schemas]
  (->> schemas
       (map-indexed (fn [index _]
                      [index (gensym (str "value'-" index))]))
       (into (sorted-map))))

(defn fields->tuple
  "Generates code which reconstructs a tuple from its fields."
  [fields]
  (vec (vals fields)))

(defmulti processor
  "Generates processor code."
  (fn [processor-type env] processor-type))