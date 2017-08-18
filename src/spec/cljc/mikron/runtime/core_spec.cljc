(ns mikron.runtime.core-spec
  "`mikron.compiler.core` spec namespace."
  (:require [clojure.spec.alpha :as s]
            [macrowbar.core :as macrowbar]))

(macrowbar/emit :debug
  (s/def ::defschema-args
    (s/cat :schema-name           qualified-keyword?
           :schema+global-options (s/* any?))))