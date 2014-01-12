(ns design-project.Models.company
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; ちゃんと値のチェックもする

;;  Listで
(def company-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-company-data
  "add company data in list.
  when add list, inclement id
  return
  list in company data."
  [com id]
  (send company-data conj (assoc com :id id)))


;; insert
(defn insert 
  "insert company table.
  parameter
   com-map attribute 
    :name company name.
  return 
   generate id"
  [com-map]
  (add-company-data com-map 
                    (:generated_key 
                      (first 
                        (jdbc/insert! my-db :company com-map)))))

;; select
;; filter かけれるようにする予定
(defn select 
  "select from company table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from company"]))

(comment
  ;; sample
  (insert {:name "company"})
  (select))
