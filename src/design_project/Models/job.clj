(ns design-project.Models.job
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))



(def job-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-job-data
  "add job data in list.
  when add list, inclement id
  return
  list in job data."
  [com id]
  (send job-data conj (assoc com :id id)))


;; insert
(defn insert 
  "insert job table.
  parameter
   job-map attribute
    :name 
  return
   generate id"
  [job-map]
  (add-job-data job-map
                (:generated_key
                  (first
                    (jdbc/insert! my-db :job job-map)))))

;; select
(defn select 
  "select from job table.
  return
   select data in map"
  []
	(jdbc/query my-db
		["select * from job"]))


;; sample
(insert {:name "仕事"})
(select)
