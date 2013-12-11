(ns design-project.Models.department
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [department-map]
  "insert department-map table.
  parameter
   department-map attribute
   :name department name
  return 
   generate id"
	(jdbc/insert! my-db :department department-map))

;; select
(defn db-select []
  "select from department table.
  return
   select data in map."
	(jdbc/query my-db
		["select * from department"]))

(insert {:name "dep"})

(db-select)
