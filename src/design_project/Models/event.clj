(ns design-project.Models.event
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; ちゃんと値のチェックもする

(def event-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-event-data
  "add event data in list.
  when add list, inclement id
  return
  list in event data."
  [com id]
  (send event-data conj (assoc com :id id)))

;; insert
;; 日時
;; 場所
;; イベント種類
(defn insert 
  "insert event table.
  parameter
   event-map attribute
   :day
   :place
   :type_id foreign key references event_type table.
  return
   generate id"
  [event-map]
  (add-event-data event-map
                  (:generated_key
                    (first
                      (jdbc/insert! my-db :event event-map)))))


(defn update 
  "update event table.
  parameter
   want update id and change attribute.
  return
   1 is accept.
   exception is fail"
  [id event-map]
  (jdbc/update! my-db :event event-map ["id=?" id]))


;; select
(defn select []
  (jdbc/query my-db
              ["select * from event, event_type 
                where event.type_id = event_type.id"]))

