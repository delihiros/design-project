(ns design-project.Models.event-type
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))



(def event-type-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-event-type-data
  "add event-type data in list.
  when add list, inclement id
  return
  list in event-type data."
  [com id]
  (send event-type-data conj (assoc com :id id)))


;;insert
(defn insert
 "insert event-type table.
 parameter
  event-type-map attribute
   :name 
 return 
  generate id"
  [event-type-map]
  (add-event-type-data event-type-map
                       (:generated_key
                         (first
                           (jdbc/insert! my-db :event_type event-type-map)))))

;; select
(defn select 
  "select from event-type table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from event_type"]))

(comment
  ;; sample
  (insert {:name "送別会"})
  (select))
