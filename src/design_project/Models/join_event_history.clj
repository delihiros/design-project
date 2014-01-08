(ns design-project.Models.join-event-history
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
(defn insert 
  "insert join_event_history table.
  parameter
   history-map attribute
    :event_id foreign key references event table.
    :user_id foreign key references user table.
  return 
   generate id"
  [history-map]
  (jdbc/insert! my-db :join_event_history history-map))

;; select
(defn select 
  "select from join_event_history table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from join_event_history, event, user
                where join_event_history.event_id = event.id,
                and join_event_history.user_id = user.id"]))


