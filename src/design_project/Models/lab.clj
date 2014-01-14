(ns design-project.Models.lab
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))



(def lab-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-lab-data
  "add lab data in list.
  when add list, inclement id
  return
  list in lab data."
  [com id]
  (send lab-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; insert
(defn insert 
  "insert lab table.
  parameter 
   lab-map attribute
    :name
  return 
   generate id"
  [lab-map]
  (add-lab-data lab-map
                (:generated_key
                  (first
                    (jdbc/insert! my-db :lab lab-map)))))

;; select
(defn select 
  "select from lab table.
  return 
   select data in map."
  []
	(jdbc/query my-db
		["select * from lab"]))

(comment
  ;; sample
  (insert {:name "研究室"})
  (select))
