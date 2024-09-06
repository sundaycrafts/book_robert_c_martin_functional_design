(ns ch-8.gossip-bus-driver)

(defn make-driver [names route rumors]
    (assoc {} :name name :route (cycle route) :rumors rumors))

(defn move-driver [driver]
    (update driver :route rest))

(defn move-drivers [world]
    (map move-driver world))

(defn spread-rumors [world]
    world ; TODO: implement
    )

;; Move each drivers who in the world to the next stop.
;; The world is represented as a vector of drivers.
;; It returns a new world that after drivers has moved.
(defn drive [world]
    (-> world move-drivers spread-rumors))
