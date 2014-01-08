(ns design-project.Models.event-read
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
;; ユーザーid
;; イベントid
(defn insert 
  "insert event-read table.
  parameter
   event-read-map attribute
   :user_id foreign key references user table.
   :event_id foreign key references event table.
  return 
   generate id"
  [event-read-map]
  (jdbc/insert! my-db :event_read event-read-map))

;; select
(defn select 
  "select from event-read table.
  return
   select data in map"
  []
  (jdbc/query my-db
              ["select * from event_read, user, event
                where event_read.user_id = user.id,
                and event_read.event_id = event.id"]))


