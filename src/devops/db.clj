(ns devops.db
  (:require [datalevin.core :as d]
            [tick.core :as t]
            [cljc.java-time.instant :as ti]
            [electric-starter-app.db :refer [db-dir username]]))

(def schema {:body {:db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/fulltext true
                    :db.fulltext/autoDomain true}
             :tags #:db{:valueType :db.type/keyword :cardinality :db.cardinality/many}
             :user/name {:db/valueType :db.type/string
                         :db/cardinality :db.cardinality/one
                         :db/unique :db.unique/identity}
             :user/in {:db/valueType :db.type/ref
                       :db/cardinality :db.cardinality/one
                       :db/isComponent true}})

(def options {:validate-data? true
              :closed-schema? true
              :auto-entity-time? true})

(def conn (d/create-conn db-dir schema options))

(defn create-user [username]
  (d/transact! conn [{:db/id -1 :body "Edit me."}
                     {:db/id -2 :user/name username :user/in -1}]))

(create-user username)

(comment

  (d/q '[:find ?ib
         :where
         [?e :user/name "estevo"]
         [?e :user/in ?i]
         [?i :body ?ib]]
       (d/db conn))
  (d/transact! conn
               [{:body "test" :tags [:test-tag :another-test-tag]}])

  (d/q '[:find ?c ?u ?t
         :in $ ?body
         :where
         [?e :body ?body]
         [?e :db/created-at ?c]
         [?e :db/updated-at ?u]
         [?e :tags ?t]]
       (d/db conn)
       "test")

  ;; => (at the time I run this)
  ;;
  ;; #{[1736095214289 1736095214289 :test-tag]
  ;;   [1736095214289 1736095214289 :another-test-tag]}

  ;; Datalevin :db/created-at and :db/updated-at are unix timestamps in
  ;; milliseconds. Here's how we convert them to `t/instant`s and back.

  (t/instant 1736095214289)
  ;; => #time/instant "2025-01-05T16:40:14.289Z"
  (type *1)
  ;; => java.time.Instant
  (ti/to-epoch-milli (t/instant 1736095214289))
  ;; => 1736095214289

  )
