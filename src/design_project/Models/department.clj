(ns design-project.Models.department
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))

;;  Listで
(def department-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-department-data
  "add department data in list.
  when add list, inclement id
  return
  list in department data."
  [com id]
  (send department-data conj (assoc com :id id)))


;; insert
(defn insert [department-map]
  "insert department-map table.
  parameter
   department-map attribute
   :name department name
  return 
   generate id"
  (add-department-data department-map
                       (:generated_key
                         (first
                           (jdbc/insert! my-db :department department-map)))))

;; select
(defn select []
  "select from department table.
  return
   select data in map."
	(jdbc/query my-db
		["select * from department"]))

