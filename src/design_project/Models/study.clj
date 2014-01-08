(ns design-project.Models.study
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))



(def study-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-study-data
  "add study data in list.
  when add list, inclement id
  return
  list in study data."
  [com id]
  (send study-data conj (assoc com :id id)))

;; insert
(defn insert 
  "insert study table.
  parameter
   study-map attribute
    :name
  return
   generate id"
  [study-map]
  (add-study-data study-map
                  (:generated_key
                    (first
                      (jdbc/insert! my-db :study study-map)))))

;; select
(defn select 
  "select from study table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from study"]))
