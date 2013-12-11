(ns design-project.Models.industry_type
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [industry]
	(jdbc/insert! my-db :industry_type
		{:name (:name industry)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from industry_type"]))

(insert {:name "s"})

(db-select)