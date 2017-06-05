(ns app.code
  (:require [goog.net.XhrIo :as xhr]
            [clojure.string :as string]))

(def repos [{:name    "Maria"
             :repo    "mhuebert/maria"
             :website "https://maria.cloud"
             :intro   "An educational coding environment for beginners, in collaboration with [Dave](http://www.daveliepmann.com/) and [Jack](http://jackrusher.com/)."}
            {:name    "Re-View"
             :website "https://re-view.io"
             :repo    "re-view/re-view"
             :intro   "A simple toolset for building React apps in ClojureScript."}
            {:name    "CLJS Live"
             :repo    "mhuebert/cljs-live"
             :website "https://cljs-live.matt.is"
             :intro   "A utility for bundling and loading dependencies into the ClojureScript self-hosted compiler."}
            {:name  "Magic Tree"
             :repo  "mhuebert/magic-tree"
             :intro "A starting point for structural editors in ClojureScript. Features usable implementations of bracket highlighting and some paredit commands."}
            ])