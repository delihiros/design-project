(ns design-project.Models.course
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert [course]
	(jdbc/insert! my-db :course
		{:name (:name course)}))

;; select
(defn db-select []
	(jdbc/query my-db
		["select * from course"]))

(insert {:name "cou"})

(db-select)