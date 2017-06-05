(ns functions.scratch)

;; messing with graphql syntax ideas


(defn emit [value]
  (cond (string? value) (str \" value \")
        :else value))

(defn query [n {:keys [query/as] :as opts}]
  (let [s (name n)
        s (if as (str (name as) ": " s) s)
        s (if-not (empty? (dissoc opts :as))
            (str s "(" (apply str (interpose ", " (map (fn [[k v]]
                                                         (str (name k) ": " (emit v))) (seq (dissoc opts :query/as))))) ")")
            s)]
    s))


'{(query :repository {:query/as (camelCase repo)
                      :owner    owner
                      :name     name})
  (into [:nameWithOwner
         :description
         :homepageUrl
         :pushedAt
         (query :url {:first 100})]
        some-fragment)}

'{:query/query :repository
  :query/as    (camelCase repo)
  :query/args  {:owner owner
                :name  name}}

(defn query
  ([name])
  ([name alias-or-options])
  ([name alias options]))

'(query :description)
'(query :fluff :description)
'(query :fluff :description {:name name})
'(query :fluff {:name name})

'{(-> :repository
      (q/alias (camelCase repo))
      (q/args {:owner owner
               :name  name}))
  {:nameWithOwner             {}
   :description               {}
   :homepageUrl               {}
   :pushedAt                  {}
   (q/args :url {:first 100}) {}}}

{:latestPost {:_id      {}
              :title    {}
              :content  {}
              :author   {:name {}}
              :comments {:content {}
                         :author  {:name {}}}}}

{:me {:name {}}}

'{(query :human {:id 1000})
  {:name                     {}
   (-> :height
       (query {:unit FOOT})) {}}}

(def comparison-fields
  {:name      true
   :appearsIn true
   :friends   {:name true}})

'{(-> :hero
      (q/args {:episode episode}))
  {:name                                         true
   (-> :friends
       (q/directive :include {:if withFriends})) {:name true}}}

'{(q/mutation :CreateReviewForEpisode {:$ep     Episode!
                                       :$review ReviewInput!})
  (q/args :createReview {:episode :$ep
                         :review  :$review}) #_{:stars      true
                                              :commentary true}}

'(q/mutation CreateReviewForEpisode [^Episode! $ep
                                     ^ReviewInput! $review]
             {(-> :createReview
                  (q/args {:episode $episode
                           :review  $review})) {:stars      true
                                                :commentary true}})

'(q/mutation CreateReviewForEpisode [^Episode! $ep
                                     ^ReviewInput! $review]
             {[:createReview {:episode $episode
                              :review  $review}]
              {:stars      {}
               :commentary {}}})

'(q/query HeroForEpisode [^Episode! $ep]
          {(q/args :hero {:episode $ep})
           {:name       {}
            :__typename {}
            :on/Droid   {:primaryFunction {}}
            :on/Human   {:height {}}}})

'(q/query HeroForEpisode [^Episode! $ep]
          {[:hero {:episode $ep}]
           {:name       true
            :__typename true
            :on/Droid   {:primaryFunction true}
            :on/Human   {:height true}}})







;; variables are supported directly by graphQL
;; - not sure if we need to bother with them
;;
;; types are important in graphql
;; - unsure of implications here
;;
;; directives
;; @include... impact is specified by server.
;; general question:
;; graphql is designed for string queries, doing parsing etc. on the server.
;; are there disadvantages to using 'local'/client Clojure and ignoring
;; these features of graphql?

;; mutation fields run in series, not parallel

;; union types
