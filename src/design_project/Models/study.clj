(ns design-project.Models.study
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert study table.
  parameter
   study-map attribute
    :name
  return
   generate id"
  [study-map]
	(jdbc/insert! my-db :study study-map))

;; select
(defn select 
  "select from study table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from study"]))

