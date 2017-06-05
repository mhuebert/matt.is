(ns app.photos
  (:require [re-view.core :as v :refer [defview]]
            [re-view-material.icons :as icons]
            [goog.dom.classes :as classes]))

(defn link [filename]
  (str "/images/photos/" filename))

(def photos ["bg_sky_only.jpg"
             "banana.jpg"
             "Guatemala-002.jpg"
             "Guatemala-006.jpg"
             "fire.jpg"
             "plane.jpg"
             "kids.jpg"
             "snowbird.jpg"
             "moonvalley.jpg"
             "baby.jpg"
             "bird.jpg"
             "waves.jpg"
             "epp.jpg"
             "Victoria-003.jpg"
             "cheryl.jpg"
             "flower.jpg"
             "matt-epp.jpg"])


(defview fixed-content
  {:spec/props        {:on-close {:spec :Function
                                  :doc  "Function will be called when full-screen mode is exited."}}
   :spec/children     [{:spec :Element
                        :doc  "Element will be displayed centered on a full-screen, fixed background"}]
   :life/did-mount    #(classes/add js/document.body "overflow-hidden")
   :life/will-unmount #(classes/remove js/document.body "overflow-hidden")}
  [{:keys [on-close]} content]
  [:.fixed.left-0.right-0.top-0.bottom-0.z-999.flex.items-center
   {:on-click #(when (let [current-target (aget % "currentTarget")]
                       (or (= current-target (aget % "target"))
                           (= current-target (aget % "target" "parentNode"))
                           (classes/has (aget % "target") ".fixed-content-close")))
                 (on-close))
    :style    {:background-color "rgba(255,255,255,0.95)"
               :overflow-y       "auto"}}
   [:.mw8.center-l.w-100.ph3
    {:style {:max-height "100%"}}
    [:.border-box.relative
     {:class "bg-white b--light-gray"}
     content]]])

(defview photo-list
  {:life/initial-state {:index 0}}
  [{:keys [view/state]}]
  (let [filename (nth photos (:index @state))
        total (count photos)
        {:keys [index advancing? timeout expanded?]} @state
        back (when (not= index 0) #(do (.preventDefault %)
                                       (when timeout (js/clearTimeout timeout))
                                       (swap! state assoc
                                              :advancing? -1
                                              :timeout (js/setTimeout (fn []
                                                                        (swap! state assoc
                                                                               :index (dec index)
                                                                               :advancing? nil)) 300))))
        forward (when (not= index (dec total))
                  #(do (.preventDefault %)
                       (when timeout (js/clearTimeout timeout))
                       (swap! state assoc :advancing? 1
                              :timeout (js/setTimeout (fn []
                                                        (swap! state assoc
                                                               :index (inc index)
                                                               :advancing? nil)) 300))))]
    (cond->>
      [:div
       [:.mv3.overflow-hidden.relative.shadow-4.lh-0
        [:.absolute.top-0.right-0.z-1.white.o-50.pa2.hover-o-90
         {:on-click #(swap! state update :expanded? not)}
         (-> (if expanded? icons/Close icons/ArrowExpand)
             (icons/class "fixed-content-close pointer")
             (icons/size 30))]
        [:div
         {:style {:width         "300%"
                  :transition    (when-not (nil? advancing?) "margin-left 0.3s ease-in-out")
                  :margin-bottom -1
                  :margin-left   (case advancing?
                                   nil "-100%"
                                   -1 0
                                   1 "-200%")}}
         [:.dib.w-third (when back
                          [:img.w-100 {:src (link (nth photos (dec (:index @state))))}])]
         [:.dib.w-third.relative
          [:img.w-100 {:src           (link filename)
                       :on-mouse-down (if-not expanded?
                                        #(swap! state assoc :expanded? true)
                                        forward)
                       :style         {:cursor (cond (not expanded?) "zoom-in"
                                                     forward "e-resize"
                                                     :else nil)}}]]
         [:.dib.w-third (when forward
                          [:img.w-100 {:src (link (nth photos (inc (:index @state))))}])]]]
       [:.flex.relative
        [:.flex-auto]
        (-> icons/ArrowBack
            (icons/size 40)
            (update 1 merge (if back
                              {:on-mouse-down back
                               :class         "pointer"}
                              {:class "moon-gray"})))

        (-> (icons/style icons/ArrowBack {:transform "rotate(180deg)"})
            (icons/size 40)
            (update 1 merge (if forward
                              {:on-mouse-down forward
                               :class         "pointer "}
                              {:class "moon-gray"})))
        [:.flex-auto]]]
      expanded? (fixed-content {:on-close #(swap! state dissoc :expanded?)}))))