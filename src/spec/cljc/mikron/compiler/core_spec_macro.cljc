(ns mikron.compiler.core-spec-macro
  "`mikron.compiler.core-specs` macro namespace."
  (:require [clojure.spec.alpha :as s]
            [macrowbar.core :as macrowbar])
  #?(:cljs (:require-macros [mikron.compiler.core-spec-macro])))

(macrowbar/emit :debug
  (defmacro schema-spec*
    "Helper macro for shorthand schema spec definition."
    [options & fields]
    `(s/and (s/or ~@(when (empty? fields)
                      [:simple `(s/and keyword? (s/conformer vector))])
                  :complex (s/and (s/cat :name    keyword?
                                         :options (s/? (s/nilable (s/keys :opt-un ~options)))
                                         ~@fields)
                                  (s/conformer (juxt :name
                                                     #(or (:options %) {})
                                                     ~@(take-nth 2 fields)))))
            (s/conformer second))))
