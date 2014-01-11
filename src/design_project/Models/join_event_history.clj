(ns design-project.Models.join-event-history
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; ちゃんと値のチェックもする

(def join-event-history-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-join-event-history-data
  "add join-event-history data in list.
  when add list, inclement id
  return
  list in join-event-history data."
  [com id]
  (send join-event-history-data conj (assoc com :id id)))



;; insert
(defn insert 
  "insert join_event_history table.
  parameter
   history-map attribute
    :user_id foreign key references user table.
    :event_id foreign key references event table.
  return 
   generate id"
  [history-map]
  (add-join-event-history-data history-map
                               (:generated_key
                                 (first
                                   (jdbc/insert! my-db :join_event_history history-map)))))

;; select
(defn select 
  "select from join_event_history table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from join_event_history, event, user
                where join_event_history.event_id = event.id
                and join_event_history.user_id = user.id"]))

;; sample
(insert {:user_id 1
         :event_id 1})

(select)
