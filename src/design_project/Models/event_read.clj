(ns design-project.Models.event-read
  (:use [design-project.Models.database]
        [design-project.Models.valid])
  (:require [clojure.java.jdbc :as jdbc]))


;; select
(defn select-all
  "select from event-read table.
  return
   select data in map"
  []
  (jdbc/query my-db
              ["select * from event_read, user, event
                where event_read.user_id = user.id
                and event_read.event_id = event.id"]))

(def event-read-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-event-read-data
  "add event-read data in list.
  when add list, inclement id
  return
  list in event-read data."
  [com id]
  (send event-read-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :user_id :event_id]) input)
       ((not-null? [:user_id :event_id]) input)
       (valid-values? input)
       (foreign-key-exist? :user {:id (:user_id input)})
       (foreign-key-exist? :event {:id (:event_id input)})))


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
  (if (is-valid? event-read-map)
    (add-event-read-data event-read-map
                         (:generated_key
                           (first
                             (jdbc/insert! my-db :event_read event-read-map))))))

(comment
  ;; sample
  (insert {:user_id 0
          :event_id 1})


  (select-all))
