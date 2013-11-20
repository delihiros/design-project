(ns design-project.Models.company
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
(defn insert [com]
  (jdbc/insert! my-db :company
                {:name (:name com)}))

;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from company"]))

(insert {:name "t"})

( select)
