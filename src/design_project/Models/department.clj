(ns design-project.Models.department
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [department]
	(jdbc/insert! my-db :department
		{:name (:name department)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from department"]))

(insert {:name "dep"})

(db-select)