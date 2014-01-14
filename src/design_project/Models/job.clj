(ns design-project.Models.job
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))


;; select
(defn select-all
  "select from job table.
  return
   select data in map"
  []
	(jdbc/query my-db
		["select * from job"]))



(def job-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-job-data
  "add job data in list.
  when add list, inclement id
  return
  list in job data."
  [com id]
  (send job-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; insert
(defn insert 
  "insert job table.
  parameter
   job-map attribute
    :name 
  return
   generate id"
  [job-map]
  (if (is-valid? job-map)
    (add-job-data job-map
                (:generated_key
                  (first
                    (jdbc/insert! my-db :job job-map))))))
(comment
  ;; sample
  (insert {:name "仕事"})
  (select-all))
