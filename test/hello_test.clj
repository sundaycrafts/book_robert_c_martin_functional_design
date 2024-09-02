(ns hello-test
    (:require [clojure.test :as t]))

(t/deftest test-assertion
  ;; fail test example
  ;; (t/testing  "true equals false"
  ;;   (t/is (= true false)))
  (t/testing "true is true"
      (t/is (true? true))))
