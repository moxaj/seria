(ns mikron.test-util
  (:require [clojure.walk :as walk]
            [mikron.runtime.processor.validate :as runtime.processor.validate]))

(defn nan?
  "Returns `true` if value is NaN, `false otherwise`."
  [value]
  #?(:clj  (Double/isNaN value)
     :cljs (js/isNaN value)))

#?(:cljs
   (defn arraybuffer->seq
     "Converts an ArrayBuffer `value` to a sequence."
     [value]
     (seq (.from js/Array (js/Int8Array. value)))))

(defn clean*
  "Returns a more 'equal friendly' value, if necessary."
  [value]
  (condp contains? (type value)
    #{runtime.processor.validate/binary-type}
    [:mikron/binary #?(:clj  (seq value)
                       :cljs (arraybuffer->seq value))]

    #?(:clj  #{java.lang.Double java.lang.Float}
       :cljs #{js/Number})
    (if (nan? value)
      :mikron/nan
      (double value))

    value))

(defn clean
  "Returns a more 'equal friendly' value, if necessary."
  [value]
  (walk/postwalk clean* value))

(defn equal?
  "Checks whether the given values are equal."
  [value-1 value-2]
  (= (clean value-1)
     (clean value-2)))
