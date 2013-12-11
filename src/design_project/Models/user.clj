(ns design-project.Models.user
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
;; 大学id
;; ユーザー状態
;; イメージ画像
;; 氏名
;; パスワード
;; ハッシュ
;; 国籍
;; 住所
;; 電話番号
;; 職業ID
;; 業種ID
;; 生年月日
;; 性別
;; 希望課程ID
;; 希望コースID
;; 志望教員名
;; 入学済み
;; 入学日
;; 学年
;; 学部ID
;; 研究科ID
;; 研究室ID
;; 学籍番号
;; 保証人名
;; 保証人住所
;; 保証人電話番号
;; 修了日
(defn insert [user-set]
  (jdbc/insert! my-db :user user-set))

(defn update 
  "update user table.
  parameter
   want update id and change attribute.
  return
   1 is accept.
   exception is fail"
  [id user-set]
  (jdbc/update! my-db :user user-set ["id=?" id]))



;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from user"]))



