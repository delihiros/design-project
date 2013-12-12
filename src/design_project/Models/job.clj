(ns design-project.Models.job
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert job table.
  parameter
   job-map attribute
    :name 
  return
   generate id"
  [job-map]
	(jdbc/insert! my-db :job job-map))

;; select
(defn select 
  "select from job table.
  return
   select data in map"
  []
	(jdbc/query my-db
		["select * from job"]))

