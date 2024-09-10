(ns ch-05.atom-2)

(def counter (atom 0)) ; atomic shared value

(defn add-one [x]
    (let [y (inc x)]
    (print (str "(" x ")"))
    y))

(defn increment [n id]
    (dotimes [_ n]
        (print id)
        ;; swap! applies the (atomic) value to the function in thread-safe way without locking value
        ;; by swapping the original value after applied it to the function if the current value
        ;; haven't been changed yet. If it have been changed, swap! retries the same process again.
        (swap! counter add-one)))

(let [ta (future (increment 10 "a"))
    tx (future (increment 10 "x"))
    _ @ta ; wait for completion
    _ @tx] ; wait for completion
    (println "\nCounter is: " @counter))

;; => ax(0)(0)a(1)(1)x(2)(2)a(3)(3)x(4)(4)a(5)(5)x(6)(6)a(7)(7)x(8)(8)a(9)(9)x(10)(10)a(11)(11)x(12)(12)a(13)(13)x(14)(14)x(15)(15)a(16)(16)x(17)(17)a(18)(18)(19)
;; => Counter is:  20
