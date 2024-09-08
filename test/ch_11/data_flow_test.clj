(ns ch-11.data-flow-test
    (:require [clojure.test :as t]
        [ch-11.data-flow :as sut]))

(t/deftest data-flow-assertion
    (t/testing "executes file"
        (t/is (= [] (sut/execute-file {:mode :stub} "Hello world"))))
  )
