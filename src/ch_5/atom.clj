(ns ch-5.atom)

(defn memo [f]
  (let [mem (atom {})]
    (fn [& args]
      (if-let [e (find @mem args)]
        (val e)
        (let [ret (apply f args)]
          (swap! mem assoc args ret)
          ret)))))

(defn fib [n]
  (if (<= n 1)
    n
    (+ (fib (dec n)) (fib (- n 2)))))

;; (time (fib 35))
;; => "Elapsed time: 91.182792 msecs"

(def fib (memo fib)) ;; def (not defn) are evaluated only once when passed same params

;; (time (fib 35))
;; => "Elapsed time: 0.2215 msecs"
