(ns design-project.Models.event
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


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
  (jdbc/insert! my-db :event event-map))


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
              ["select * from event"]))


