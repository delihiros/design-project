(ns design-project.Models.company
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする

;; insert
(defn insert 
  "insert company table.
  com-map attribute 
   :name company name.
  return 
   generate id"
  [com-map]
  (jdbc/insert! my-db :company com-map))

;; update
(defn update [id com-map]
  (jdbc/update! my-db :company com-map ["id=?" id]))

;; select
;; filter かけれるようにする予定
(defn db-select 
  "select from company table.
  return
   select data in map."
  (jdbc/query my-db
              ["select * from company"]))

(insert {:name "t"})

(db-select)


(update 1 {:name "update"})


