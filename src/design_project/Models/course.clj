(ns design-project.Models.course
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;; insert
(defn insert 
  "insert course table.
  parameter
   course-map attribute
    :name course name
  return 
   generate id"
  [course-map]
	(jdbc/insert! my-db :course course-map))

;; select
(defn select []
  "select from course table.
  return
   select data in map."
	(jdbc/query my-db
		["select * from course"]))

(db-select)
