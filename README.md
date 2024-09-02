# Code Examples of Functional Design: Principles, Patterns, and Practices [978-0138176396]

## Clojure basic (for macOS)

```shell
# Install Clojure tool-chain
brew install clojure/tools/clojure

# Execute script decleared as `(ns hello)` name space file in src/ directory
clj -X hello/run
```

* `deps.edn` - dependency file

## Basic syntax

See [the official docs](https://clojure.org/guides/deps_and_cli) for more details.

### Name space / using other name space libraries

```clj
(ns hello
  (:require [java-time.api :as t]))

(defn time-str
  "Returns a string representation of a datetime in the local time zone."
  [instant]
  (t/format
    (t/with-zone (t/formatter "hh:mm a") (t/zone-id))
    instant))

(defn run [opts]
  (println "Hello world, the time is" (time-str (t/instant))))
```
