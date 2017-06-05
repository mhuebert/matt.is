(ns functions.index
  (:require [cljs.nodejs :as nodejs]
            [app.code :as code]
            [clojure.string :as string]))

(def functions (nodejs/require "firebase-functions"))

(defn camelCase [s]
  (string/replace s #"-(.)" (fn [[_ x]] (string/upper-case x))))


(defn repos-query [repos]
  (str "{"
       (->> (for [repo repos]
              (let [[owner name] (string/split repo "/")]
                (str "{
  " (camelCase repo) ": repository(owner: \"" owner "\", name: \"" name "\") {
    nameWithOwner
    description
    url
    homepageUrl
    pushedAt
  }
}
")))
            (string/join "\n"))
       "}"))

(defn repos [req res]
  (.set res "Cache-Control" "public, max-age 1800, s-maxage=3600")
  (.send res (str "Hello, world! " code/repos)))

(aset js/exports "getRepos" (.. functions -https (onRequest repos)))