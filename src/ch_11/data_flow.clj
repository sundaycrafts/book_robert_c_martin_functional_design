(ns ch-11.data-flow
    (:require [clojure.string :as string])
    (:require [clojure.string :as string]))

(defmulti read-from :mode)

(defn noop [state]
    (update state :cycles conj (:x state)))

(defn addx [n state]
    (let [{:keys [x cycles]} state]
        (assoc state :x (+ x n)
            :cycles (vec (concat cycles [x x])))))

(defn execute [state lines]
    (if (empty? lines)
        state
        (let [line (first lines)
            state (if (re-matches #"noop" line)
                (noop state)
                (if-let [[_ n] (re-matches #"addx (-?\d+)" line)]
                    (addx (Integer/parseInt n) state)
                    "TILT"))]
            (recur state (rest lines)))))

; mock for CRT display behaviour
(defn render-cycles [cycles]
    (loop [cycles cycles
        screen "" ; screen buffer
        t 0 ; cycle number
        ]
    (if (empty? cycles)
        ;; this partition splits strings to lists of 40 char items each.
        ;; the second 40 means step, which a distance between the first numbers of each partition.
        ;; e.g. (partition 3 2 (range 20))
        ;; ; => ((0 1 2) (2 3 4) (4 5 6) ...)
        ;;        ↑       ↑       ↑
        ;; distances for each first numbers are 2 in the example.
        (map #(apply str %) (partition 40 40 "" screen))
        (let [x (first cycles)
            offset (- t x)
            ; offset is in -1, 0, 1 (-1 <= offset <= 1).
            ; In other words, offset is in the window rendered pixel
            pixel? (<= -1 offset 1)
            screen (str screen (if pixel? "#" "."))
            t (mod (inc t) 40)]
        (recur (rest cycles) screen t)))))

(defn execute-file [mode file-name]
    (let [lines (string/split-lines (read-from mode file-name))
        starting-state {:x 1 :cycles []} ; state acts as a buffer to render cycles at once
        ending-state (execute starting-state lines)]
    (:cycles ending-state)))

(defmethod read-from :file [_ path]
    (slurp path))

(defmethod read-from :stub [_ contents]
    contents)

(defn print-screen [lines]
     (doseq [line lines]
         (println line))
     true)

;; (defn -main []
;;     (-> "input"
;;         execute-file
;;         render-cycles
;;         print-screen))
