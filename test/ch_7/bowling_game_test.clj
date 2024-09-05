(ns ch-7.bowling-game-test
    (:require [clojure.test :as t]
        [ch-7.bowling-game :as sut]))

(t/deftest test-bowling-game
    (t/testing "gutter"
        (t/is (= (sut/score (repeat 20 0)) 0)))
    (t/testing "1 point for all rolls"
        (t/is (= (sut/score (repeat 20 1)) 20)))
    ;; (t/testing "1 speare, 0 pins rest of theme"
    ;;     (t/is (= (sut/score (concat [5 5 7] (repeat 17 0))) 24)))
    )
