(ns mikron.test-runner.node
  "Node test runner."
  (:require [clojure.test :as test]
            [cljs.nodejs :as nodejs]
            [mikron.buffer-test]
            [mikron.compiler.core-test]
            [mikron.runtime.core-test]))

(nodejs/enable-util-print!)

(defmethod test/report [::test/default :summary] [{:keys [fail error] :as summary}]
  (println "Test summary: " summary)
  (.exit nodejs/process (if (= 0 fail error) 0 1)))

(test/run-tests 'mikron.buffer-test
                'mikron.compiler.core-test
                'mikron.runtime.core-test)
