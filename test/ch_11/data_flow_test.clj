(ns ch-11.data-flow-test
    (:require [clojure.test :as t]
        [ch-11.data-flow :as sut]))

(t/deftest data-flow-assertion
    (t/testing "executes file"
        (t/is (= [1 1 1] (sut/execute-file {:mode :stub} "noop\nnoop\nnoop"))))

    (t/testing "addx consumes two cycles and updates register x"
      (t/is (= {:x 5, :cycles [1 2 2]} (sut/addx 3 {:x 2, :cycles [1]}))))

    (t/testing "noop extract register x's value and append it to the cycles of the buffer state"
      (t/is (= {:x 1, :cycles [1]} (sut/noop {:x 1 :cycles []}))))
  )
