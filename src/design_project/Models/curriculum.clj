(ns design-project.Models.curriculum
	(:use [design-project.Models.database])
	(:require [clojure.java.jdbc :as jdbc]))
  
;;  Listで
(def curriculum-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-curriculum-data
  "add curriculum data in list.
  when add list, inclement id
  return
  list in curriculum data."
  [com id]
  (send curriculum-data conj (assoc com :id id)))


;; insert
(defn insert 
  "insert curriculum table.
  parameter
   curriculum attribute
   :name curriculum name.
  return
   generate id."
  [curriculum-map]
  (add-curriculum-data curriculum-map
                       (:generated_key
                         (first	
                           (jdbc/insert! my-db :curriculum curriculum-map)))))

;; select
(defn select 
  "select from curriculum table.
  return 
   select data in map."
  []
	(jdbc/query my-db
		["select * from curriculum"]))

(comment
  ;; sample
  (insert {:name "かりきゅらみゅ"})

  (select))
