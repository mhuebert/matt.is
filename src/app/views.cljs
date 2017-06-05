(ns app.views
  (:require [re-view.core :as v :refer-macros [defview view]]
            [re-db.d :as d]
            [re-view-material.icons :as icons]
            [re-view-material.util :as util]
            [app.markdown :as md]
            [app.code :as code]
            [app.photos :as photos]
            [clojure.string :as string]))


(def hr :.bb.b--darken-2.mv3)
(def sub-heading :h2)
(def list-container :.shadow-4.mb4.mt3)
(def list-white-item :.bg-white.ph3.bb.b--near-white.overflow-hidden.pv2)
(def list-title-link :a.f4)

(defn icon-link [{:keys [href icon label]}]
  [:a.no-blue.flex.items-center.mv2
   {:href href}
   (icons/class icon "mr2")
   label])

(defn slug [s]
  (-> (string/lower-case s)
      (string/replace #"[\s.]+" "-")
      (string/replace #"[^\w-]+" "")
      (string/replace #"\-+" "-")
      (string/replace #"^\-+" "")
      (string/replace #"\-+$" "")))

(defview layout
  [_ & content]
  [:div

   [:.mw7.center.serif.blue-links.ph4-ns.ph3
    [:.tc.mv4
     [:.f1.mv2 [:a.no-blue {:href "/"} "Matt Huebert"]]
     [:div
      "Berlin, Germany."
      [:div [:.inline-flex.items-center
             [:a.no-blue.mh1.black {:href "https://www.twitter.com/mhuebert"} icons/Twitter]
             [:a.no-blue.mh1.black {:href "https://www.github.com/mhuebert"} (icons/size icons/GitHub 20)]
             [:a.no-blue.mh1.black {:href "mailto:me@matt.is"} icons/Mail]]]]

     [hr]
     [:p.i "Life is an experiment," [:br] "please be kind."]
     [hr]]
    #_[:.lh-copy.mv3
       (->> (for [[href label] [["/about" "About"]
                                ["/code" "Code"]
                                ["/photos" "Photos"]
                                ["/writing" "Writing"]]]
              [:a.ma1 {:key  href
                       :href href} label])
            (interpose " • "))]

    content


    #_[:.flex.items-center.relative.bg-gray
       [:.pv4.pt5.fw2.tc.serif.tc.flex-auto.ph4.mh3
        [:.f1.mv3 "Matt.is"]]]]])



(def writing [["Trying to Matter" {:excerpt "The pool is filled with algae because it was abandoned; it was abandoned because of war."}],
              ["Kurt Cobain on Time" {:excerpt "…time and a person's ability to understand time is very important. It's the only way I can relate to you the very real way a person becomes addicted to substances."}],
              ["Rewrite a Rant" {:excerpt "Really short answer: you're ignorant."}],
              ["The Trudge" {:excerpt "You'd cover your head with the pillow, sink your consciousness deep into the fluff."}]])

(def prior-work [["Introducing BrainTripping" {:excerpt "…an open-ended game of storytelling, parody, personality and poetry."}],
                 ["Introducing Hacking Health" {:excerpt "…what kind of atmosphere would be most conducive to breaking down the barriers between these two disciplines?"}],
                 ["Introducing Overlap.me" {:excerpt "The frustration I’m ultimately trying to solve is how to meet new people."}],])

(defn writing-list [items]
  (for [[title
         {:keys [excerpt]}] items]
    [list-white-item
     [:.mv3
      [list-title-link {:key  title
                        :href (str "/writing/" (slug title))} title]]
     (when excerpt
       [:.mv3.i excerpt])]))



(def code-list
  (for [{:keys [name
                repo
                intro
                website]} code/repos]
    [list-white-item
     [:.flex.mv2.flex-column.flex-row-ns
      [:.f4.mv2 name]
      [:.flex-auto]
      [:.pl3-ns.flex-none
       {:style {:width "12rem"}}
       (icon-link {:href  (str "https://www.github.com/" repo)
                   :label "GitHub"
                   :icon  icons/GitHub})
       (when website (icon-link
                       {:href  website
                        :icon  icons/Link
                        :label (string/replace website #"https://(.*)" "$1")}))]]
     (when intro [:.mb2.o-70.lh-copy.mw6.nt3.nb2  (md/md intro)])]))


(defview home
  []
  (layout
    [:.flex-ns.nl3-ns.nr3-ns
     [:.w-50-ns.w-100.mh3-ns
      [sub-heading "Projects"]
      [list-container code-list]
      [sub-heading "Prior Work"]
      [list-container (writing-list prior-work)]]
     [:.w-50-ns.w-100.mh3-ns
      [sub-heading "Photography"]
      (photos/photo-list)
      [sub-heading "Writing"]
      [list-container (writing-list writing)]]]

    ))

(defview writing-view
  [{:keys [slug]}]
  (layout
    (md/remote {:url (str "/writing/" slug ".md")})))