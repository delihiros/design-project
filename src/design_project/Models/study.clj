(ns design-project.Models.study
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))


;; select
(defn select-all
  "select from study table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from study"]))



(def study-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-study-data
  "add study data in list.
  when add list, inclement id
  return
  list in study data."
  [com id]
  (send study-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; insert
(defn insert 
  "insert study table.
  parameter
   study-map attribute
    :name
  return
   generate id"
  [study-map]
  (if (is-valid? study-map )
    (add-study-data study-map
                  (:generated_key
                    (first
                      (jdbc/insert! my-db :study study-map))))))
(comment
  ;; sample
  (insert {:name "課程"})
  (select-all))
