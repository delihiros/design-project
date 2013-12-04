(ns design-project.Models.lab
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [lab]
	(jdbc/insert! my-db :lab
		{:name (:name lab)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from lab"]))

(insert {:name "l"})

(db-select)