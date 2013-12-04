(ns design-project.Models.study
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [study]
	(jdbc/insert! my-db :study
		{:name (:name study)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from study"]))

(insert {:name "geology"})

(db-select)