(ns design-project.Models.event
  (:use [design-project.Models.database]
        [design-project.Models.valid])
  (:require [clojure.java.jdbc :as jdbc]))

;; select
(defn select-all []
  (jdbc/query my-db
              ["select * from event, event_type 
                where event.type_id = event_type.id"]))


(def event-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-event-data
  "add event data in list.
  when add list, inclement id
  return
  list in event data."
  [com id]
  (send event-data conj (assoc com :id id)))


(defn is-valid? [input]
  (and ((row-exist? [:id :day :name :place :type_id]) input)
       ((not-null? [:day :name :place :type_id]) input)
       (valid-values? input)
       (foreign-key-exist? :event_type {:id (:type_id input)})))


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
   :name
   :type_id foreign key references event_type table.
  return
   generate id"
  [event-map]
  (if (is-valid? event-map) 
    (add-event-data event-map
                    (:generated_key
                      (first
                        (jdbc/insert! my-db :event event-map))))))


(defn update 
  "update event table.
  parameter
   want update id and change attribute.
  return
   1 is accept.
   exception is fail"
  [id event-map]
  (jdbc/update! my-db :event event-map ["id=?" id]))


(comment
  ;; sample
  (insert {:day "2013-08-26"
          :place "東京"
          :name "学校説明会"
          :type_id 9
          })

  (select-all))
