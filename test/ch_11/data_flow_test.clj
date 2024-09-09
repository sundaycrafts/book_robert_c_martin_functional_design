(ns ch-11.data-flow-test
    (:require [clojure.test :as t]
        [ch-11.data-flow :as sut]))

(t/deftest data-flow-assertion
    (t/testing "executes file"
        (t/is (= [1 1 1] (sut/execute-file {:mode :stub} "noop\nnoop\nnoop"))))
  )
