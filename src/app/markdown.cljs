(ns app.markdown
  (:require [re-view.core :as v :refer [defview]]
            [cljsjs.markdown-it]
            [cljsjs.highlight]
            [cljsjs.highlight.langs.clojure]
            [cljsjs.highlight.langs.xml]
            [goog.object :as gobj]
            [goog.net.XhrIo :as xhr]
            [clojure.string :as string]))

(defn content-anchor [s]
  (str "__" (-> s
                (string/lower-case)
                (string/replace #"(?i)[\W-]+" "-"))))

(defn heading-anchors [md]
  (let [heading-open (aget md "renderer" "rules" "heading_open")]
    (aset md "renderer" "rules" "heading_open"
          (fn [tokens idx x y self]
            (let [heading-tokens (aget tokens (inc idx) "children")
                  anchor (->> (areduce heading-tokens i out ""
                                       (str out (aget heading-tokens i "content")))
                              (content-anchor))]
              (str (if heading-open
                     (.apply heading-open (js-this) (js-arguments))
                     (.apply (aget self "renderToken") self (js-arguments)))
                   "<a id=" anchor " class='heading-anchor' href=\"#" anchor "\"></a>"))))))


(def MD (let [MarkdownIt ((gobj/get js/window "markdownit") "default"
                           #js {"highlight" (fn [s lang]
                                              (try (-> (.highlight js/hljs (case lang "clj" "clojure"
                                                                                      "html" "html"
                                                                                      "clojure") s)
                                                       (.-value))
                                                   (catch js/Error e "")))})]
          (doto MarkdownIt
            (.use heading-anchors))))

(defn scroll-to-anchor [{:keys [view/children view/prev-children] :as this}]
  (when (not= children prev-children)
    (when-let [hash (aget js/window "location" "hash")]
      (some-> (.getElementById js/document (subs hash 1))
              (.scrollIntoView)))))

(defview md
  {:life/did-update scroll-to-anchor
   :life/did-mount  scroll-to-anchor}
  [{:keys [view/props]} s]
  [:.markdown-it.lh-copy (assoc props :dangerouslySetInnerHTML {:__html (try (.render MD s)
                                                                             (catch js/Error e s))})])

(defview remote
  {:life/will-mount         #(.fetch %)
   :life/will-receive-props #(.fetch %)
   :fetch                   (fn [{:keys [url view/state]}]
                              (when-not (= url (:url @state))
                                (xhr/send url (fn [e]
                                                (if (.isSuccess (.-target e))
                                                  (swap! state assoc
                                                         :markdown (.. e -target getResponseText)
                                                         :url url)
                                                  (swap! state assoc :markdown "Error loading")))
                                          "GET")))}
  [{:keys [view/state]}]
  (md (or (:markdown @state) "Loading...")))