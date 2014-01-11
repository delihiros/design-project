(ns design-project.Models.industry-type
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))



(def industry-type-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-industry-type-data
  "add industry-type data in list.
  when add list, inclement id
  return
  list in industry-type data."
  [com id]
  (send industry-type-data conj (assoc com :id id)))


;; insert
(defn insert 
  "insert industry-type table.
  parameter
   industry-type-map attribute
    :name
  return 
   generate id"
  [industry-type-map]
  (add-industry-type-data industry-type-map
                          (:generated_key
                            (first
                              (jdbc/insert! my-db :industry_type industry-type-map)))))

;; select
(defn select 
  "select from industry-type table.
  return
   select data in map."
  []
	(jdbc/query my-db
		["select * from industry_type"]))

(insert {:name "業種"})

(select)
