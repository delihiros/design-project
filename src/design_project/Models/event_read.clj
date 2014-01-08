(ns design-project.Models.event-read
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; ちゃんと値のチェックもする

(def event-read-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-event-read-data
  "add event-read data in list.
  when add list, inclement id
  return
  list in event-read data."
  [com id]
  (send event-read-data conj (assoc com :id id)))


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
  (add-event-read-data event-read-map
                       (:generated_key
                         (first
                           (jdbc/insert! my-db :event_read event-read-map)))))

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


