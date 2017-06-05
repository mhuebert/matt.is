(ns app.core
  (:require [cljsjs.react]
            [cljsjs.react.dom]
            [clojure.core.match :refer-macros [match]]

            [re-view.core :as v :refer-macros [defview]]
            [re-view-routing.core :as r]
            [re-db.d :as d]

            [app.views :as views]
            [re-view-material.util :as util]
            [clojure.string :as string]))

(enable-console-print!)

(defview root
  "The root component reads current router location from re-db,
   and will therefore re-render whenever this value changes."
  []
  [:div
   (util/sync-element!
     {:get-element #(.-documentElement js/document)
      :class       "bg-near-white"
      :style       {:min-height "100%"}})

   (match (d/get :router/current-location :segments)
          [] (views/home nil)
          ["writing" slug] (views/writing-view {:slug slug})
          :else (views/layout [:div "not found"]))])

(defn init []
  (r/listen
    (fn [{:keys [segments path] :as location}]
      (if (= (first segments) "images")
        (set! (.. js/window -location -href) path)
        (d/transact! [(assoc location :db/id :router/current-location)]))))
  (v/render-to-dom (root) "app"))

(defonce _ (init))
