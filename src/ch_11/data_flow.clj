(ns ch-11.data-flow
    (:require [clojure.string :as string]))

(defmulti read-from :mode)

(defn execute [starting-state lines]
    starting-state)

(defn execute-file [mode file-name]
    (let [lines (string/split-lines (read-from mode file-name))
        starting-state {:x 1 :cycles []}
        ending-state (execute starting-state lines)]
    (:cycles ending-state)))

(defmethod read-from :file [_ path]
    (slurp path))

(defmethod read-from :stub [_ contents]
    contents)
