(ns ch-5.atom-test
    (:require [clojure.test :as t]
        [ch-5.atom :as sut]))

(t/deftest test-assertion
  (t/testing "atom example 1"
      (t/is (= 9227465 (sut/fib 35)))))
