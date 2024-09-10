(ns ch-07.bowling-game-test
    (:require [clojure.test :as t]
        [ch-07.bowling-game :as sut]))

(t/deftest test-bowling-game
    (t/testing "gutter"
        (t/is (= (sut/score (repeat 20 0)) 0)))
    (t/testing "1 point for all rolls"
        (t/is (= (sut/score (repeat 20 1)) 20)))
    (t/testing "1 speare, 0 pins rest of them"
        (t/is (= (sut/score (concat [5 5 7] (repeat 17 0))) 24)))
    (t/testing "1 strike, 0 pins rest of theme"
        (t/is (= (sut/score (concat [10 2 3] (repeat 16 0))) 20)))
    (t/testing "perfect game with exceeded rolls"
        (t/is (= (sut/score (repeat 12 10)) 300)))
    )
