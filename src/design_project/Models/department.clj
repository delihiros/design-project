(ns design-project.Models.department
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))
 
;; select
  (defn select-all 
    "select from department table.
    return
    select data in map."
    []
    (jdbc/query my-db
                ["select * from department"]))


;;  Listで
(def department-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-department-data
  "add department data in list.
  when add list, inclement id
  return
  list in department data."
  [com id]
  (send department-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; insert
(defn insert 
  "insert department-map table.
  parameter
   department-map attribute
   :name department name
  return 
   generate id"
  [department-map]
  (if (is-valid? department-map)
    (add-department-data department-map
                       (:generated_key
                         (first
                          (jdbc/insert! my-db :department department-map))))))

(comment
      ;; sample
    (insert {:name "学科"})

    (select-all))
