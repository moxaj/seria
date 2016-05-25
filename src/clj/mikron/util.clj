(ns mikron.util
  "Utility functions."
  (:require [clojure.string :as string]
            [clojure.walk :as walk]))

;; coll

(defn find-by [pred form]
  (cond-> []
    (pred form)
    (conj form)

    (or (sequential? form)
        (map? form))
    (concat (mapcat (partial find-by pred) form))))

(defn find-unique-by [pred form]
  (distinct (find-by pred form)))

(defn index-of [item coll]
  (->> coll
       (map-indexed vector)
       (filter (comp (partial = item) second))
       (ffirst)))

;; schema

(defn type-of [schema & _]
  (cond
    (keyword? schema) schema
    (vector? schema)  (first schema)
    :else             nil))

(defn with-options [[a b & rest :as complex]]
  (if (and (map? b) (seq rest))
    complex
    (vec (concat [a {} b] rest))))

(defn as-set [sorted-by body]
  `(into ~(case sorted-by
            nil      `#{}
            :default `(sorted-set)
            `(sorted-set-by ~sorted-by))
         ~body))

(defn as-map [sorted-by body]
  `(into ~(case sorted-by
            nil      `{}
            :default `(sorted-map)
            `(sorted-map-by ~sorted-by))
         ~body))

(defn as-record [constructor body]
  (if constructor
    `(~constructor ~body)
    body))

;; symbol

(defn raw-gensym [sym]
  (let [sym-name (name sym)]
    (if-let [index (string/last-index-of sym-name "_")]
      (symbol (subs sym-name 0 index))
      sym)))

(defmacro with-gensyms [binding-form & body]
  `(let [~@(mapcat (fn [sym]
                     [sym `(gensym ~(str sym "_"))])
                   binding-form)]
     ~@body))

(def var-name
  (memoize
    (fn [key]
      (gensym (format "%s_" (name key))))))

(defn processor-name
  ([processor-type]
   (var-name processor-type))
  ([processor-type schema-name]
   (var-name (keyword (format "%s-%s" (name processor-type) (name schema-name))))))

(defn select-processor [processor-type schema schemas]
  `(case ~schema
     ~@(->> (keys schemas)
            (mapcat (fn [schema-name]
                      [schema-name (processor-name processor-type schema-name)]))
            (doall))))
