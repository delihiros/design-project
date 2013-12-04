(ns design-project.Models.job
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [job]
	(jdbc/insert! my-db :job
		{:name (:name job)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from job"]))

(insert {:name "Dr"})

(db-select)