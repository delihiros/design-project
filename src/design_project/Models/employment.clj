(ns design-project.Models.employment
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
;; id
;; ユーザーid
;; 会社id
;; 役職
;; 業種id
;; その他特記事項
;; 変更時間
(defn insert 
  "insert employment table.
  parameter
   employment-map attribute
   :user_id foreign key references user table.
   :company_id foreign key references company table.
   :position 役職
   :industry 業種
   :comment 
   :uptime 
 return
  generate id" 
  [employment-map]
  (jdbc/insert! my-db :employment employment-map))

;; update
(defn update 
  "update employment table.
  parameter
   want update id and change attribute.
  return
   1 is accept
   exception is fail"
  [id employment-map]
  (jdbc/update! my-db :employment employment-map ["id=?" id]))

;; select
(defn select 
  "select from employment table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from employment"]))


