(ns design-project.Models.company
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする

;; insert
(defn insert 
  "insert company table.
  parameter
   com-map attribute 
    :name company name.
  return 
   generate id"
  [com-map]
  (jdbc/insert! my-db :company com-map))


;; select
;; filter かけれるようにする予定
(defn select 
  "select from company table.
  return
   select data in map."
  (jdbc/query my-db
              ["select * from company"]))

(insert {:name "t"})

(db-select)


