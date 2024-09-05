(ns ch-6.factors-test
    (:require [clojure.test :as t]
        [ch-6.factors :as sut]))

(t/deftest test-factors
    (t/testing "factors of 1 should be empty vector"
        (t/is (= (sut/prime-factors-of 1) [])))
    (t/testing  "factors of 2 should be [2]"
        (t/is (= (sut/prime-factors-of 2) [2])))
    (t/testing "factors of 3 should be [3]"
        (t/is (= (sut/prime-factors-of 3) [3])))
    (t/testing "factors of 4 should be [2 2]"
        (t/is (= (sut/prime-factors-of 4) [2 2])))
    (t/testing "factors of 5 should be [5]"
        (t/is (= (sut/prime-factors-of 5) [5])))
    (t/testing "factors of 6 should be [2 3]"
        (t/is (= (sut/prime-factors-of 6) [2 3])))
    (t/testing "factors of 7 should be [7]"
        (t/is (= (sut/prime-factors-of 7) [7])))
    (t/testing "factors of 8 should be [2 2 2]"
        (t/is (= (sut/prime-factors-of 8) [2 2 2])))
    (t/testing "factors of 9 should be [3 3]"
        (t/is (= (sut/prime-factors-of 9) [3 3])))
    (t/testing "factors of 10 should be [2 5]"
        (t/is (= (sut/prime-factors-of 10) [2 5])))
    (t/testing "factors of 11 should be [11]"
        (t/is (= (sut/prime-factors-of 11) [11])))
    (t/testing "factors of 12 should be [2 2 3]"
        (t/is (= (sut/prime-factors-of 12) [2 2 3])))
    )
