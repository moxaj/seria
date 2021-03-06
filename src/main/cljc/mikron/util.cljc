(ns mikron.util
  "Generic utility functions."
  (:require [clojure.spec.alpha :as s]
            [macrowbar.core :as macrowbar])
  #?(:cljs (:require-macros [mikron.util])))

(defmacro safe
  "Evaluates each expression of `body` and returns `ex-value` if an exception occured.
  Otherwise returns the value of the last expression in `body`."
  [ex-value & body]
  `(try
     (do ~@body)
     (catch ~(if (macrowbar/cljs? &env) :default `Throwable) e#
       ~ex-value)))

#?(:cljs
   (defn ^boolean node-env?
     "Returns `true` if compiled for Node.js, `false` otherwise."
     []
     (= "nodejs" cljs.core/*target*)))

(macrowbar/emit :debug
  (defn enforce-spec
    "Conforms the value to the spec, or throws if it cannot do so."
    [spec value]
    (let [conformed-value (s/conform spec value)]
      (when (= :clojure.spec.alpha/invalid conformed-value)
        (let [explained-data (s/explain-data spec value)]
          (throw (ex-info (str "Spec assertion failed:\n"
                               (with-out-str (s/explain-out explained-data)))
                          explained-data))))
      conformed-value))

  (defn can-have-meta?
    "Returns `true` if `arg` can have metadata, `false` otherwise."
    [arg]
    #?(:clj  (instance? clojure.lang.IMeta arg)
       :cljs (satisfies? IMeta arg)))

  (defmacro as-boolean
    "Applies a boolean type hint to `value`, if possible."
    [value]
    (macrowbar/with-syms {:gen [^boolean value']}
      (if-not (macrowbar/cljs? &env)
        value
        `(let [~value' ~value]
           ~value')))))
