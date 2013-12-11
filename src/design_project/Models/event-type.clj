(ns design-project.Models.event-type
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert event-type table.
  parameter
   event-map attribute
   :name
  return
   generate id"
  [event-map]
	(jdbc/insert! my-db :event_type event-map))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from event_type"]))

(insert {:name "s"})

(db-select)
