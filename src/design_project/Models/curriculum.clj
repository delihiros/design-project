(ns design-project.Models.curriculum
	(:use [design-project.Models.database]
        [design-project.Models.valid])
	(:require [clojure.java.jdbc :as jdbc]))
 
;; select
(defn select-all 
  "select from curriculum table.
  return 
   select data in map."
  []
	(jdbc/query my-db
		["select * from curriculum"]))

 
;;  Listで
(def curriculum-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-curriculum-data
  "add curriculum data in list.
  when add list, inclement id
  return
  list in curriculum data."
  [com id]
  (send curriculum-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :name]) input)
       ((not-null? [:name]) input)
       (valid-values? input)))

;; insert
(defn insert 
  "insert curriculum table.
  parameter
   curriculum attribute
   :name curriculum name.
  return
   generate id."
  [curriculum-map]
  (if (is-valid? curriculum-map )
    (add-curriculum-data curriculum-map
                         (:generated_key
                           (first	
                             (jdbc/insert! my-db :curriculum curriculum-map))))))
(comment
  ;; sample
  (insert {:name "かりきゅらみゅ"})

  (select-all))
