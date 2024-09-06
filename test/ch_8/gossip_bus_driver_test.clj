(ns ch-8.gossip-bus-driver-test
    (:require [clojure.test :as t]
        [ch-8.gossip-bus-driver :as sut]))

(t/deftest test-gossip-bus-driver
    (t/testing "drives one bus at one stop"
        (let [driver (sut/make-driver "d1" [:s1] #{:r1}) ; :s1 = station, :r1 = rumor
            world [driver] ; the world is represented as a vector of drivers
            new-world (sut/drive world) ; the world after drove drivers
            ]
        (t/is (= (count new-world) 1))
        (t/is (= (-> new-world first :route first) :s1))))

    (t/testing "drives one bus at two stops"
        (let [driver (sut/make-driver "d1" [:s1 :s2] #{:r1})
            world [driver]
            new-world (sut/drive world)]
        (t/is (= (count new-world) 1))
        (t/is (= (-> new-world first :route first) :s2))))

    (t/testing "drives two buses at some stops"
        (let [d1 (sut/make-driver "d1" [:s1 :s2] #{:r1})
            d2 (sut/make-driver "d2" [:s1 :s3 :s2] #{:r2})
            world [d1 d2]
            new-1 (sut/drive world)
            new-2 (sut/drive new-1)]
        (t/is (= 2 (count new-1)))
        (t/is (= :s2 (-> new-1 first :route first)))
        (t/is (= :s3 (-> new-1 second :route first)))
        (t/is (= 2 (count new-2)))
        (t/is (= :s1 (-> new-2 first :route first)))
        (t/is (= :s2 (-> new-2 second :route first)))
        ))

    (t/testing "gets stops"
        (let [drivers #{
            {:name "d1" :route [:s1]}
            {:name "d2" :route [:s1]}
            {:name "d3" :route [:s2]}
            }]
        (t/is (= {
            :s1 [{:name "d1" :route [:s1]} {:name "d2" :route [:s1]}]
            :s2 [{:name "d3" :route [:s2]}]
            } (sut/get-stops drivers)))
        ))

    (t/testing "merges rumos"
        (t/is (= [{:name "d1" :rumors #{:r2 :r1}} {:name "d2" :rumors #{:r2 :r1}}]
            (sut/merge-rumors [{:name "d1" :rumors #{:r1}} {:name "d2" :rumors #{:r2}}]))))

    ;; use-case test from here
    (t/testing "shares gossip when drivers are at same stop"
        (let [d1 (sut/make-driver "d1" [:s1 :s2] #{:r1})
            d2 (sut/make-driver "d1" [:s1 :s2] #{:r2})
            world [d1 d2]
            new-world (sut/drive world)]
        (t/is (= 2 (count new-world)))
        (t/is (= #{:r1 :r2} (-> new-world first :rumors)))
        (t/is (= #{:r1 :r2} (-> new-world second :rumors)))))
    )
