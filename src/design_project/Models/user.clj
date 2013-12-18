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
(defn insert 
  "insert user table.
  parameter 
   user-map attribute
    :university_id foreign key references university table.
    :status state and authority are expressed.
    :image hes image file.
    :name
    :password
    :hash_method 本当に実装するのか？
    :country
    :address
    :phone phonenumber.
    :job_id foreign key references job table.
    :industry_id foreign key references industry-type table.
    :birthday
    :sex 1:man 2:woman
    :wish_curriculum_id foreign key references curriculum table.
    :wish_course_id foreign key references course table.
    :wish_teacher
    :class 学年
    :department_id foreign key references department table.
    :study_course_id foreign key references study table.
    :laboratory_id foreign key references lab table.
    :student_id 
    :guarantor_name 保証人
    :guarantor_address
    :guarantor_phone
    :finish_course_day
  return
   generate id"
  [user-map]
  (jdbc/insert! my-db :user user-map))

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
(defn select 
  "select from user table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from user"]))

