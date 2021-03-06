(ns mikron.runtime.core-test
  "Generative testing namespace."
  (:require [clojure.test :as test]
            [mikron.test-util :as test-util]
            [mikron.runtime.core :as mikron]
            [mikron.runtime.core-test-macro :refer [compile-schemas]]))

(def buffer (mikron/allocate-buffer 100000))

(defmulti test-mikron
  "Test function for :pack, :diff, :valid? and :interp processors."
  (fn [method schema values] method))

(defmethod test-mikron :pack [_ schema values]
  (mikron/with-buffer buffer
    (doseq [value values]
      (test/is (test-util/equal? value (->> value
                                            (mikron/pack schema)
                                            (mikron/unpack schema)))))))

(defmethod test-mikron :diff [_ schema values]
  (doseq [[value-1 value-2] (partition 2 values)]
    (test/is (test-util/equal? value-2 (->> value-2
                                            (mikron/diff* schema value-1)
                                            (mikron/undiff* schema value-1))))
    (test/is (test-util/equal? value-2 (->> value-2
                                            (mikron/diff schema value-1)
                                            (mikron/undiff schema value-1))))))

(defmethod test-mikron :pack+diff [_ schema values]
  (doseq [[value-1 value-2] (partition 2 values)]
    (test/is (test-util/equal? value-2 (->> value-2
                                            (mikron/diff schema value-1)
                                            (mikron/pack schema)
                                            (mikron/unpack schema)
                                            (mikron/undiff schema value-1))))))

(defmethod test-mikron :valid? [_ schema values]
  (doseq [value values]
    (test/is (mikron/valid? schema value))))

(defmethod test-mikron :interp [_ schema values]
  (doseq [[value-1 value-2] (partition 2 values)]
    ;; We don't actually test anything here
    (test/is (any? (mikron/interp schema value-1 value-2 0 1 0.5)))))

(test/deftest generative-test
  (doseq [[test-name schema]
          (compile-schemas
            {"Byte test"     :byte
             "Ubyte test"    :ubyte
             "Short test"    :short
             "Ushort test"   :ushort
             "Int test"      :int
             "Uint test"     :uint
             "Long test"     :long
             "Varint test"   :varint
             #?@(:clj ["Float test" :float])
             "Double test"   :double
             "Char test"     :char
             "Boolean test"  :boolean
             "Nil test"      :nil
             "Binary test"   :binary
             "String test"   :string
             "Keyword test"  :keyword
             "Symbol test"   :symbol
             "Any test"      :any
             "Constant test" [:constant {:foo "bar"}]
             "Enum test"     [:enum #{:cat :dog :measurement :error}]
             "Optional test" [:optional :byte]
             "Wrapped test"  [:wrapped unchecked-inc-int unchecked-dec-int :int]
             "Multi test"    [:multi number? {true :int false :string}]
             "List test"     [:list :byte]
             "Vector test"   [:vector :int]
             "Set test"      [:set :short]
             "Set < test"    [:set {:sorted-by <} :short]
             "Set > test"    [:set {:sorted-by >} :int]
             "Map test"      [:map :byte :string]
             "Map < test"    [:map {:sorted-by <} :byte :string]
             "Map > test"    [:map {:sorted-by >} :byte :string]
             "Tuple test"    [:tuple [:int :string :double]]
             "Record test"   [:record {:a :int :b :string :c :byte}]})]
    (let [values (repeatedly 100 #(mikron/gen schema))]
      (test/testing test-name
        (doseq [test-method (keys (methods test-mikron))]
          (test-mikron test-method schema values))))))

(test/deftest self-referential-test
  (let [schema (mikron/schema ::s [:tuple [:byte [:optional ::s]]])
        values (repeatedly 100 #(mikron/gen schema))]
    (doseq [test-method (keys (methods test-mikron))]
      (test-mikron test-method schema values))))

(test/deftest partial-eval-test
  (let [schema (mikron/schema ::s [:record ^eval (zipmap (map (comp keyword str) (range))
                                                         (repeat 5 :int))])
        values (repeatedly 100 #(mikron/gen schema))]
    (doseq [test-method (keys (methods test-mikron))]
      (test-mikron test-method schema values))))
