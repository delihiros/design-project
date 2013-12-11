(ns design-project.Models.industry-type
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert industry-type table.
  parameter
   industry-type-map attribute
    :name
  return 
   generate id"
  [industry-type-map]
	(jdbc/insert! my-db :industry_type industry-type-map))

;; select
(defn select 
  "select from industry-type table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from industry_type"]))

