(ns mikron.common
  "Cross-platform functions which are embedded into the generated processors."
  #?@(:clj  [(:refer-clojure :exclude [format])
             (:import [java.util Date])]
      :cljs [(:require [cljs.reader :as reader]
                       [goog.string]
                       [goog.string.format])]))

;; generic

(defn pow [base exp]
  #?(:clj  (Math/pow base exp)
     :cljs (.pow js/Math base exp)))

(defn floor [n]
  #?(:clj  (Math/floor n)
     :cljs (.floor js/Math n)))

(defn exception [^String s]
  #?(:clj  (Exception. s)
     :cljs (js/Error. s)))

(defn parse-string [^String s]
  #?(:clj  (read-string s)
     :cljs (reader/read-string s)))

(defn abs [^double n]
  #?(:clj  (Math/abs n)
     :cljs (.abs js/Math n)))

(defn round [^double n]
  #?(:clj  (Math/round n)
     :cljs (.round js/Math n)))

(defn format [s & args]
  #?(:clj  (apply clojure.core/format s args)
     :cljs (apply goog.string.format s args)))

(defn date->long [#?(:clj ^Date date :cljs date)]
  (.getTime date))

(defn long->date [^long time]
  #?(:clj  (Date. time)
     :cljs (js/Date. time)))

(defn raw? [^bytes value]
  #?(:clj  (instance? (Class/forName "[B") value)
     :cljs (instance? js/ArrayBuffer value)))

;; gen

(def symbol-chars
  (map char (concat [\_ \- \? \!]
                    (range 97 123)
                    (range 65 91)
                    (range 48 58))))

(defn random-integer [bytes signed?]
  (let [max-value (pow 2 (* bytes 8))
        r         (floor (* max-value (rand)))]
    (long (if-not signed?
            r
            (- r (/ max-value 2))))))

(defn gen-raw []
  (let [byte-seq (repeatedly (+ 10 (rand-int 10))
                             #(random-integer 1 true))]
    #?(:clj  (byte-array byte-seq)
       :cljs (aget (js/Int8Array. (apply array byte-seq))
                   "buffer"))))

;; diff

(defrecord DiffedValue [value])

(defn diffed? [value]
  (instance? DiffedValue value))

(defn wrap-diffed [value]
  (->DiffedValue value))

(defn unwrap-diffed [value]
  {:pre [(diffed? value)]}
  (:value value))

;; interp

(defn interp-numbers [value-1 value-2 time-factor]
  #?(:clj  (long (+' value-1 (* time-factor (-' value-2 value-1))))
     :cljs (+ value-1 (* time-factor (- value-2 value-1)))))