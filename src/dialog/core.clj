(ns dialog.core
  (:refer-clojure :rename {== is-eq?})
  (:use clojure.core.logic)
  (:require [clojure.string :as str]))
            ;[clojure.core.logic.fd :as fd]))

;; TODO

;; Load logic streams from Redis?
;; Key = word
;; Value =
;;    Sorted Set of words sorted by frequency
;;    of trigram/bigram

;; Raw data

(def ngrams
  (->> (slurp "resources/10000_ngrams.txt")
    (#(str/split % #"\n"))
    (map str/trim)
    (map #(str/split % #" "))
    (reverse)
    (map (fn [[x & xs]] (vec (cons (Integer. x) xs))))))

(def bigrams
  (filter #(= 2 (count (drop 1 %))) ngrams))

(def trigrams
  (filter #(= 3 (count (drop 1 %))) ngrams))

;; Data -> Relations

(defn bigramo [f w1 w2]
  (fn [a]
    (to-stream
      (map
        #(unify a % [f w1 w2])
        bigrams))))

(defn trigramo [f w1 w2 w3]
  (fn [a]
    (to-stream
      (map
        #(unify a % [f w1 w2 w3])
        trigrams))))

;; Pure relations

(defne sentenceo [length words]
  ([0 []])
  ([1 [w]]
    (fresh [f w1 w2]
      (conde
        [succeed (bigramo f w1 w)]
        [succeed (trigramo f w1 w2 w)])))
  ([2 [w1 w2]]
    (fresh [f]
      (bigramo f w1 w2)))
  ([3 [w1 w2 w3]]
    (fresh [f]
      (trigramo f w1 w2 w3)))
  ([len [w1 w2 w3 . ws]]
    (fresh [f1 f2 res]
      (bigramo f1 w1 w2)
      (bigramo f2 w2 w3)
      (project [len]
        (conso w3 ws res)
        (sentenceo (- len 2) res)))))

;; Functional wrapper

(defn sentences
  [length]
  (->> (run* [q] (sentenceo length q))
    (map (partial str/join " "))))

