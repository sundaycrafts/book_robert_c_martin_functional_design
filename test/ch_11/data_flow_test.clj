(ns ch-11.data-flow-test
    (:require [clojure.test :as t]
        [ch-11.data-flow :as sut]))

(t/deftest data-flow-assertion
    (t/testing "addx consumes two cycles and updates register x"
      (t/is (= {:x 5, :cycles [1 2 2]} (sut/addx 3 {:x 2, :cycles [1]}))))

    (t/testing "noop extract register x's value and append it to the cycles of the buffer state"
      (t/is (= {:x 1, :cycles [1]} (sut/noop {:x 1 :cycles []}))))

    (t/testing "executes lines to resolve new state"
        (t/is (= {:x 14, :cycles [1 1 4 4 4 4 14]} (sut/execute {:x 1, :cycles []} [ "addx 3" "noop" "noop" "addx 10" "noop"]))))

    (t/testing "renders cycles when only matches cycle number and register x value"
        (t/is (= ["##.###."] (sut/render-cycles [1 1 4 4 4 4 14]))))

    (t/testing "executes file"
        (t/is (= [1 1 1] (sut/execute-file {:mode :stub} "noop\nnoop\nnoop"))))
  )
