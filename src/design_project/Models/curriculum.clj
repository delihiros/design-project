(ns design-project.Models.curriculum
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [curriculum]
	(jdbc/insert! my-db :curriculum
		{:name (:name curriculum)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from curriculum"]))

(insert {:name "t"})

(db-select)