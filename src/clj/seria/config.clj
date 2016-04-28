(ns seria.config
  "Code generation from static config."
  (:require [seria.validate :as validate]
            [seria.codegen.diff :as diff]
            [seria.codegen.pack :as pack]
            [seria.codegen.unpack :as unpack]
            [seria.codegen.interp :as interp]
            [seria.codegen.gen :as gen]))

(defn make-processors* [{:keys [schemas] :as options}]
  (->> (keys schemas)
       (mapcat (fn [schema-name]
                 [(pack/make-inner-packer schema-name options)
                  (pack/make-inner-packer schema-name (assoc options :diffed? true))
                  (pack/make-packer schema-name options)
                  (pack/make-packer schema-name (assoc options :diffed? true))
                  (unpack/make-inner-unpacker schema-name options)
                  (unpack/make-inner-unpacker schema-name (assoc options :diffed? true))
                  (diff/make-differ schema-name options)
                  (diff/make-undiffer schema-name options)
                  (gen/make-generator schema-name options)
                  (interp/make-interper schema-name options)]))
       (concat [(unpack/make-unpacker options)])))

(defmacro make-processors [config]
  (let [processors (make-processors* (validate/validate-config config))]
    `(letfn [~@processors]
       (hash-map ~@(->> processors
                        (map first)
                        (filter (comp not :private meta))
                        (mapcat (fn [processor-name]
                                  [(keyword processor-name) processor-name])))))))

(defmacro defprocessors [names & {:as config}]
  (let [processors (gensym)]
    `(let [~processors (make-processors ~config)]
       ~@(map (fn [name]
                `(def ~name (~(keyword name) ~processors)))
              names))))
