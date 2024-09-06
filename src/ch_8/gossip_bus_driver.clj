(ns ch-8.gossip-bus-driver
    (:require [clojure.set :as set]))

(defn make-driver [names route rumors]
    (assoc {} :name name :route (cycle route) :rumors rumors))

(defn move-driver [driver]
    (update driver :route rest))

(defn move-drivers [world]
    (map move-driver world))

(defn get-stops [world]
    (loop [world world
        stops {}]
    (if (empty? world)
        stops
        (let [driver (first world)
            stop (first (:route driver))
            ;; update stop in stops by `(conj <entry having :key in stops> driver)`.
            ;; in more varbose: update (or insert if not exists yet) stops' field that having an :key
            ;; with `(conj existing_vactor_having_key driver)`. driver form is like {:name "~" :route [:key]}.
            ;; eventually, it returns like { :key [{:name "~" :route [:key]}] }
            stops (update stops stop conj driver)
            ]
        ;; loop with world that excluded first driver to scan one-by-one.
        ;; stops behaves as an accumurator.
        (recur (rest world) stops)
        ))))

(defn merge-rumors [drivers]
    (let [rumors (map :rumors drivers) ; new vector only having :rumors' value
        all-rumors (apply set/union rumors) ; new vector excluded duplicated keys
        ]
    ;; #(...) is a shorthand to define lambda instead of using `fn` keyword.
    ;; `%` inside of lambda represends an argument. use %1, %2 for multiple arguments.
    ;; `assoc <target map> <key> <value>` overrides a (or inserts new) key-value pair
    (map #(assoc % :rumors all-rumors) drivers)))

(defn spread-rumors [world]
    (let [stops-with-drivers (get-stops world) ; transform drivers list into stops list {:stop1 [[driver attr]] :stop2 [[driver attr]]}
        ;; vals returns a list of all values (not keys) in the map. keys returns list of its keys.
        ;; result: [[drivers at stop1] [drivers at stop2]]
        drivers-by-stop (vals stops-with-drivers)
        ]
    (flatten (map merge-rumors drivers-by-stop)) ; flatten makes nested list to be flat
    ))

;; Move each drivers who in the world to the next stop.
;; The world is represented as a vector of drivers.
;; It returns a new world that after drivers has moved.
(defn drive [world]
    (-> world move-drivers spread-rumors))

(defn drive-till-all-rumors-spread [world]
    (loop [world (drive world)
            time 1]
        (cond
            (> time 480) :never
            ;; `=` checks if every driver has the same set of rumors
            ;; If this condition is true (i.e., all drivers have the same rumors), the function returns the current `time`.
            (apply = (map :rumors world)) time
            :else (recur (drive world) (inc time)))))
