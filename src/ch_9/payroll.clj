(ns ch-9.payroll)

;; interfaces and dispatchers
(defn get-pay-class [employee]
    (first (:pay-class employee)))

(defn get-disposition [paycheck-directive]
    (first (:disposition paycheck-directive)))

(defmulti is-today-payday :schedule)
(defmulti calc-pay get-pay-class)
(defmulti dispose get-disposition)

;; supporting functions
(defn get-employees-to-be-paid-today [today employees]
    (filter #(is-today-payday % today) employees))

(defn- build-employee [db employee] ; defn- represents a private function for this name space
    (assoc employee :db db))

(defn get-employees [db]
    (map (partial build-employee db) (:employees db))) ; partial returns a curried function. (fn [employee]) for here.

(defn create-paycheck-directives [ids payments dispositions]
    (map #(assoc {} :id %1 :amount %2 :disposition %3) ids payments dispositions))

(defn send-paychecks [ids payments dispositions]
    (for [paycheck-directive
        (create-paycheck-directives ids payments dispositions)]
    (dispose paycheck-directive))) ; dispose means action, disposition is a information for dispose.

;; getters
(defn get-paycheck-amounts [employees]
    (map calc-pay employees))

(defn get-ids [employees]
    (map :id employees))

(defn get-dispositions [employees]
    (map :disposition employees))

;; facade
(defn payroll [today db]
    (let [employees (get-employees db)
        employees-to-pay (get-employees-to-be-paid-today today employees)
        amounts (get-paycheck-amounts employees-to-pay)
        ids (get-ids employees-to-pay)
        dispositions (get-dispositions employees-to-pay) ; dispositions - way to pay
        ]
    (send-paychecks ids amounts dispositions)))

;; details (outside of architectual border line).
;; put them on other name space in the real product
(defn- get-salary [employee]
    (second (:pay-class employee)))

(defmethod calc-pay :salaried [employee] ; impl which invoked when dispatcher (get-pay-class) returns :slaried
    (get-salary employee))

(defmethod calc-pay :hourly [employee]
    (let [db (:db employee)
        time-cards (:time-cards db)
        my-time-cards (get time-cards (:id employee)) ; `get` gets value from time-cards by index ((:id employee))
        [_ hourly-rate] (:pay-class employee) ; [_ hourly-rate] is an expression of distributed binding (destructuring). underscore _ means ignoring
        hours (map second my-time-cards)
        total-hours (reduce + hours)]
    (* total-hours hourly-rate)))

(defmethod calc-pay :commissioned [employee]
    (let [db (:db employee)
        sales-receipts (:sales-receipts db)
        my-sales-receipts (get sales-receipts (:id employee))
        [_ base-pay commission-rate] (:pay-class employee)
        sales (map second my-sales-receipts)
        total-sales (reduce + sales)
        ]
    (+ (* total-sales commission-rate) base-pay)
    ))

(defmethod is-today-payday :weekly [_ today]
    true)

(defmethod is-today-payday :biweekly [_ today]
    true)

(defmethod is-today-payday :monthly [_ today]
    true)

(defmethod dispose :paymaster [paycheck-directive]
    (println "disposing paycheck...")
    {:type :deposit
   :id (:id paycheck-directive)
   :paymaster (second (:disposition paycheck-directive))
   :amount (:amount paycheck-directive)})

(defmethod dispose :mail [paycheck-directive]
    (println "disposing paycheck...")
    {:type :mail
   :id (:id paycheck-directive)
   :name (second (:disposition paycheck-directive))
   :address (nth (:disposition paycheck-directive) 2)
   :amount (:amount paycheck-directive)})

(defmethod dispose :deposit [paycheck-directive]
    (println "disposing paycheck...")
  {:type :deposit
   :id (:id paycheck-directive)
   :routing (second (:disposition paycheck-directive))
   :account (nth (:disposition paycheck-directive) 2)
   :amount (:amount paycheck-directive)})
