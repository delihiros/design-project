(ns design-project.Models.lab
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert lab table.
  parameter 
   lab-map attribute
    :name
  return 
   generate id"
  [lab-map]
	(jdbc/insert! my-db :lab lab-map))

;; select
(defn select 
  "select from lab table.
  return 
   select data in map."
  []
	(jdbc/query my-db
		["select * from lab"]))

