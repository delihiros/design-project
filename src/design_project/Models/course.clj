(ns design-project.Models.course
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))
 
;; select
(defn select-all 
  "select from course table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from course"]))


;;  Listで
(def course-data (agent (select-all)))

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
	(if (is-valid? course-map)
      (add-course-data course-map
    (:generated_key 
      (first
        (jdbc/insert! my-db :course course-map))))))

(comment
  ;; sample
  (insert {:name "course"})

  (select-all))
  
