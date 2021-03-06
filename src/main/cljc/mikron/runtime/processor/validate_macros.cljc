(ns mikron.runtime.processor.validate-macros
  (:require [mikron.math :as math])
  #?(:cljs (:require-macros [mikron.runtime.processor.validate-macros])))

(defmacro valid-integer?
  "Returns `true` if `value` is a valid integer."
  [value bytes signed?]
  `(and (int? ~value)
        (let [~value (unchecked-long ~value)]
          (and (>= ~value ~(math/lower-bound bytes signed?))
               (<  ~value ~(math/upper-bound bytes signed?))))))
