(ns mikron.compiler.util-macros
  #?(:cljs (:require-macros [mikron.compiler.util-macros])))

(defmacro compile-time
  [& body]
  (when #?(:clj  (not (:ns &env))
           :cljs true)
    `(do ~@body)))