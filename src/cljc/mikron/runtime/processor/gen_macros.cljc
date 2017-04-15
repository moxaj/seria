(ns mikron.runtime.processor.gen-macros
  (:require [mikron.compiler.util :as compiler.util]
            [mikron.runtime.math :as math])
  #?(:cljs (:require-macros [mikron.runtime.processor.gen-macros])))

(defmacro gen-integer
  "Generates an integer."
  [bytes signed?]
  (compiler.util/with-gensyms [r]
    `(let [~r (math/rand)]
       (-> (* ~r ~(math/upper-bound bytes signed?))
           (+ (* (- 1 ~r) ~(math/lower-bound bytes signed?)))
           (math/floor)
           (unchecked-long)))))
