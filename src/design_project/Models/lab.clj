(ns design-project.Models.lab
	(:use [design-project.Models.database])
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


;; insert
(defn insert 
  "insert lab table.
  parameter 
   lab-map attribute
    :name
  return 
   generate id"
  [lab-map]
  (add-lab-data lab-data
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

