(ns design-project.Models.university
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert university table.
  parameter
   uni-map attribute
    :name
  return
   generate id"
  [uni-map]
	(jdbc/insert! my-db :university uni-map))

;; select
(defn select 
  "select from university table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from university"]))

(insert {:name "s"})

(db-select)
