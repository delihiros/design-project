(ns design-project.Models.university
	(:use [design-project.Models.database])
        [design-project.Models.valid]
	(:require [clojure.java.jdbc :as jdbc]))

(def university-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-university-data
  "add university data in list.
  when add list, inclement id
  return
  list in university data."
  [com id]
  (send university-data conj (assoc com :id id)))
 
(defn is-valid [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; insert
(defn insert 
  "insert university table.
  parameter
   uni-map attribute
    :name
  return
   generate id"
  [uni-map]
  (add-university-data uni-map
                       (:generated_key
                         (first
                           (jdbc/insert! my-db :university uni-map)))))

;; select
(defn select 
  "select from university table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from university"]))

(comment
  ;; sample
  (insert {:name "大学"})
  (select))
