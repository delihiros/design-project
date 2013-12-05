(ns design-project.Models.employment
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
(defn insert [employment]
  (jdbc/insert! my-db :employment
                {:user_id (:user_id employment),
                 :company_id (:company_id employment),
                 :position (:position employment),
                 :industry (:industry employment),
                 :comment (:comment employment),
                 :uptime (:uptime employment)}))

;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from company"]))

(insert {:name "t"})

( select)
