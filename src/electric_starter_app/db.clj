(ns electric-starter-app.db
  (:require [datalevin.core :as d]))

(def db-dir
  (str (System/getProperty "user.home") "/dtlv"))

(def username "estevo")

(defn conn []
  (d/get-conn db-dir))

(defn ?in [db username]
  (d/q '[:find ?ib .
         :in $ ?u
         :where
         [?e :user/name ?u]
         [?e :user/in ?i]
         [?i :body ?ib]]
       db
       username))

(defn !in [username body]
   (let [eid (d/q '[:find ?i .
                    :in $ ?u
                    :where
                    [?e :user/name ?u]
                    [?e :user/in ?i]]
                  (d/db (conn))
                  username)]
     (d/transact! (conn)
                  [[:db/add eid :body body]])))

(comment
  (?in (d/db (conn)) "estevo")
  ;; => "Edit me."
  (!in "estevo" "edited")
  (?in (d/db (conn)) "estevo")
  ;; => "edited"
  )
