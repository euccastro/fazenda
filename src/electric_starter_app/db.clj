(ns electric-starter-app.db
  (:require [datalevin.core :as d]))

(def db-dir
  (str (System/getProperty "user.home") "/dtlv"))

(def username "estevo")

(defn conn []
  (d/get-conn db-dir))

(defn in
  ([username]
   (d/q '[:find ?ib .
          :in $ ?u
          :where
          [?e :user/name ?u]
          [?e :user/in ?i]
          [?i :body ?ib]]
        (d/db (conn))
        username))
  ([username body]
   (let [eid (d/q '[:find ?i .
                    :in $ ?u
                    :where
                    [?e :user/name ?u]
                    [?e :user/in ?i]]
                  (d/db (conn))
                  username)]
     (d/transact! (conn)
                  [[:db/add eid :body body]]))))

(comment
  (in "estevo")
  ;; => "Edit me."
  (in "estevo" "So I did!")
  (in "estevo")
;; => "So I did!"
  )
