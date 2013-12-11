(ns design-project.Models.curriculum
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert curriculum table.
  parameter
   curriculum attribute
   :name curriculum name.
  return
   generate id."
  [curriculum-map]
	(jdbc/insert! my-db :curriculum curriculum-map))

;; select
(defn select 
  "select from curriculum table.
  return 
   select data in map."
  []
	(jdbc/query my-db
		["select * from curriculum"]))

(insert {:name "t"})

(db-select)
