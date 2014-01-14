(ns design-project.Models.course
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))

;;  Listで
(def course-data (agent ()))

(defn is-valid? [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-course-data
  "add course data in list.
  when add list, inclement id
  return
  list in course data."
  [com id]
  (send course-data conj (assoc com :id id)))

;; insert
(defn insert 
  "insert course table.
  parameter
   course-map attribute
    :name course name
  return 
   generate id"
  [course-map]
	(add-course-data course-map
    (:generated_key 
      (first
        (jdbc/insert! my-db :course course-map)))))

;; select
(defn select []
  "select from course table.
  return
   select data in map."
	(jdbc/query my-db
		["select * from course"]))

(comment
  ;; sample
  (insert {:name "course"})

  (select)
  )
