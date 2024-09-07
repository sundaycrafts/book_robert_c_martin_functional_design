(ns ch-9.payroll-test
    (:require [clojure.test :as t]
        [clojure.instant :as instant]
        [ch-9.payroll :as sut]))

(t/deftest test-payroll
    (t/testing "pays one salaried employee at end of month by mail"
        (let [employees [{
                :id "emp1"
                :schedule :monthly
                :pay-class [:salaried 5000]
                :disposition [:mail "name" "home"]}]
            db {:employees employees}
            today (instant/read-instant-date "2021-11-30T00:00:00Z")]
        (t/is (= [{
            :type :mail
            :id "emp1"
            :name "name"
            :address "home"
            :amount 5000
            }] (sut/payroll today db))))
        )
    )
