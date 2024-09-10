(ns ch-06.factors)

(defn prime-factors-of [n]
    ;; works until 9
    ;; (if (> n 1)
    ;;     (if (zero? (rem n 2))
    ;;         (cons 2 (prime-factors-of (quot n 2)))
    ;;         (if (zero? (rem n 3))
    ;;            (cons 3 (prime-factors-of (quot n 3))) ; for given 9
    ;;            [n]))
    ;;     []))
    (loop [n n
        divisor 2
        factors []]
        (if (> n 1)
            (if (zero? (rem n divisor))
                (recur (quot n divisor) divisor (conj factors divisor)) ; try continue to use 2 if no remainder
                (recur n (inc divisor) factors)) ; use next of 2 (= 3) if divisor doesn't work for n
            factors)))

;; Final form of Java
;; private List<Integer> factorsOf(int n) {
;;     ArrayList<Integer> factors = new ArrayList<>(); // state here
;;
;;     for (int divisor = 2; n > 1; divisor++) {
;;         for (; n % divisor == 0; n /= divisor) {
;;             factors.add(divisor);
;;         }
;;     }
;;
;;     return factors;
;; }
