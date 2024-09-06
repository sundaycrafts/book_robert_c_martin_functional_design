(ns ch-8.gossip-bus-driver-test
    (:require [clojure.test :as t]
        [ch-8.gossip-bus-driver :as sut]))

(t/deftest test-gossip-bus-driver
    (t/testing "success compile"
        (t/is (= true true)))
    (t/testing "drives one bus at one stop"
        (let [driver (sut/make-driver "d1" [:s1] #{:r1})
            world [driver]
            new-world (sut/drive world)]
        (t/is (= (count new-world) 1))
        (t/is (= (-> new-world first :route first) :s1))))
    )
