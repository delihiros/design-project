(ns design-project.Models.university
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [uni]
	(jdbc/insert! my-db :university
		{:name (:name uni)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from university"]))

(insert {:name "s"})

(db-select)