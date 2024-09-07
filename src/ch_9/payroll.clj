(ns ch-9.payroll)

(defn get-employees [db]
    (:employees db))

(defn get-employees-to-be-paid-today [day employees]
    employees)

(defn get-paycheck-amounts [employees]
    (map (constantly 5000) employees))

(defn get-ids [employees]
    (map :id employees))

(defn get-dispositions [employees]
    (map :disposition employees))

(defn send-paychecks [ids amounts dispositions]
    (map (fn [id amount disposition]
        {:type :mail
         :id id
         :name "name"
         :address "home"
         :amount amount})
        ids amounts dispositions))

(defn payroll [today db]
    (let [employees (get-employees db)
        employees-to-pay (get-employees-to-be-paid-today today employees)
        amounts (get-paycheck-amounts employees-to-pay)
        ids (get-ids employees-to-pay)
        dispositions (get-dispositions employees-to-pay) ; dispositions - way to pay
        ]
    (send-paychecks ids amounts dispositions)))
