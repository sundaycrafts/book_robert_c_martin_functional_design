# Code Examples of Functional Design: Principles, Patterns, and Practices [978-0138176396]

## Install Clojure (for macOS)

```shell
# Install Clojure tool-chain
brew install clojure/tools/clojure

# Execute script decleared as `(ns hello)` name space file in src/ directory
clj -X hello/run
```

* `deps.edn` - dependency file

## Basic usage

See [the official docs](https://clojure.org/guides/deps_and_cli) for more details.

### REPL

```shell
clj

## Example usage and outputs:
# clj
# Clojure 1.11.4
# user=> (require '[clojure.test :as t])
# nil
# user=> (t/is (true? true) "true is true")
# true
# user=> (t/is (= true false) "true equals false")
#
# FAIL in () (NO_SOURCE_FILE:1)
# true equals false
# expected: (= true false)
#   actual: (not (= true false))
# false
# user=> ...
```

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

### Running tests

See [this blog](https://tonitalksdev.com/how-to-get-started-with-tdd-in-clojure) for absolutely begginers for Clojure.

Make sure that you have installed [cognitect-labs/test-runner](https://github.com/cognitect-labs/test-runner)
and setting up the alias (see test-runner docs for more detail).

```shell
clj -X:test
```

### Launching as a script

`-M` will pass the file as a script to the `clojure.main` process

```shell
clj -M /path/to/file.clj
```

### Other tips

```shell
# find out available libs' versions
clj -X:deps find-versions :lib clojure.java-time/clojure.java-time
```
