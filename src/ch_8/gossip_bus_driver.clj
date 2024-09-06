(ns ch-8.gossip-bus-driver)

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

(defn spread-rumors [world]
    world ; TODO: implement
    )

;; Move each drivers who in the world to the next stop.
;; The world is represented as a vector of drivers.
;; It returns a new world that after drivers has moved.
(defn drive [world]
    (-> world move-drivers spread-rumors))
