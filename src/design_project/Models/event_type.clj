(ns design-project.Models.event-type
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;;insert
(defn insert
 "insert event-type table.
 parameter
  event-type-map attribute
   :name 
 return 
  generate id"
  [event-type-map]
  (jdbc/insert! my-db :event_type event-type-map))

;; select
(defn select 
  "select from event-type table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from event_type"]))

