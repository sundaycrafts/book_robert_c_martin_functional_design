(ns ch-09.payroll-test
    (:require [clojure.test :as t]
        [clojure.instant :as instant]
        [ch-09.payroll :as sut]))

(t/deftest test-payroll
    (t/testing "pays one salaried employee at end of month by mail" ; fixed salary (no flex salary)
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

    (t/testing "pays one hourly employee on Friday by Direct Deposit"
        (let [
            employees [{
                :id "empid"
                :schedule :weekly
                :pay-class [:hourly 15]
                :disposition [:deposit "routing" "account"]}]
            time-cards {"empid" [[
                    "2022-11-12T00:00:00Z"
                    80/10 ; 80/10 represents an exactly rational number (fractional number), not a *result* of 80/10
                    ]]}
            db {:employees employees :time-cards time-cards}
            friday (instant/read-instant-date "2022-11-18T00:00:00Z")
        ]
        (t/is (= [{
            :type :deposit
            :id "empid"
            :routing "routing"
            :account "account"
            :amount 120
            }] (sut/payroll friday db)))
        ))

    (t/testing "pays one commissioned employee on an even Friday by Paymaster" ; commissioned = fixed salary + flex salary
        (let [
            employees [{
                :id "empid"
                :schedule :biweekly
                :pay-class [:commissioned 100 5/100]
                :disposition [:paymaster "paymaster"]}]
            time-cards {"empid" [[
                    "2022-11-12T00:00:00Z"
                    15000
                    ]]}
            sales-receipts {"empid" [["2022-11-12T00:00:00Z" 15000]]}
            db {:employees employees :sales-receipts sales-receipts}
            friday (instant/read-instant-date "2022-11-18T00:00:00Z")
        ]
        (t/is (= [{
            :type :deposit
            :id "empid"
            :paymaster "paymaster"
            :amount 850
            }] (sut/payroll friday db)))
        ))
    )
