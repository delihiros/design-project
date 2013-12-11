(ns design-project.Models.event_type
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [event]
	(jdbc/insert! my-db :event_type
		{:name (:name event)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from event_type"]))

(insert {:name "s"})

(db-select)