(ns mikron.compiler.core.spec
  "`mikron.compiler.core` spec namespace."
  (:require [clojure.spec.alpha :as s]
            [macrowbar.core :as macrowbar]
            [mikron.compiler.core.spec-macros :refer [schema-spec*]]
            [mikron.compiler.schema :as schema]))

(macrowbar/compile-time
  (s/def ::sorted-by
    some?)

  (s/def ::type
    (s/+ symbol?))

  (defn raw-schema-name
    "Returns the name of `schema`."
    [schema]
    (let [schema-name (if (vector? schema)
                        (first schema)
                        schema)]
      (cond
        (schema/schema-names schema-name)
        schema-name

        :else
        :custom)))

  (defmulti schema-spec
    "Returns a spec for a schema definition."
    raw-schema-name :hierarchy #'schema/extended-hierarchy)

  (defmethod schema-spec :simple [_]
    (schema-spec* []))

  (defmethod schema-spec :enum [_]
    (schema-spec* [] :values (s/coll-of keyword? :kind set?)))

  (defmethod schema-spec :optional [_]
    (schema-spec* [] :schema ::schema))

  (defmethod schema-spec :wrapped [_]
    (schema-spec* [] :pre    some?
                     :post   some?
                     :schema ::schema))

  (defmethod schema-spec :multi [_]
    (schema-spec* [] :selector some?
                     :schemas  (s/map-of any? ::schema)))

  (defmethod schema-spec :coll [_]
    (schema-spec* [] :schema ::schema))

  (defmethod schema-spec :set [_]
    (schema-spec* [::sorted-by] :schema ::schema))

  (defmethod schema-spec :map [_]
    (schema-spec* [::sorted-by] :key-schema ::schema
                                :val-schema ::schema))

  (defmethod schema-spec :tuple [_]
    (schema-spec* [] :schemas (s/coll-of ::schema :kind vector?)))

  (defmethod schema-spec :record [_]
    (schema-spec* [::type] :schemas (s/map-of keyword? ::schema)))

  (defmethod schema-spec :custom [_]
    some?)

  (s/def ::schema
    (s/and (s/multi-spec schema-spec
                         (fn [schema tag]
                           (if (= :custom tag)
                             schema
                             (let [tag' (rand-nth (schema/leaf-children schema/extended-hierarchy tag))]
                               (if (keyword? schema)
                                 tag'
                                 (into [tag'] (rest schema)))))))
           #(empty? (descendants schema/extended-hierarchy %))))

  (s/def ::paths
    (s/and (s/or :tuple       (s/map-of nat-int? ::paths)
                 :record      (s/map-of keyword? ::paths)
                 :multi       (s/map-of any? ::paths)
                 :coll-or-map (s/keys :req-un [:paths/all])
                 :true        true?)
           (s/conformer second)))

  (s/def :paths/all ::paths)

  (s/def ::diff-paths ::paths)

  (s/def ::interp-paths ::paths)

  (s/def ::processor-types (s/coll-of keyword? :kind set?))

  (s/def ::compile-schema-args
    (s/and (s/cat :schema ::schema
                  :exts   (s/keys* :opt-un [::diff-paths ::interp-paths ::processor-types]))
           (s/conformer
             (fn [{:keys [exts] :as schema-args}]
               (reduce (fn [schema-args ext]
                         (assoc schema-args ext (get exts ext)))
                       (dissoc schema-args :exts)
                       #{:diff-paths :interp-paths :processor-types}))))))