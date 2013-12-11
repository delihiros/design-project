(ns design-project.Models.user
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
;; 大学id
;; ユーザー状態
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
  (jdbc/insert! my-db :user
                {:university_id (:university_id user-set),
                 :status (:status user-set),
                 :name (:name user-set),
                 :password (:password user-set),
                 :hash_method (:hash_method user-set),
                 :country (:country user-set),
                 :address (:address user-set),
                 :phone (:phone user-set),
                 :job_id (:job_id user-set),
                 :industry_id (:industry_id user-set),
                 :birthday (:birthday user-set),
                 :sex (:sex user-set),
                 :wish_curriculum (:wish_curriculum user-set),
                 :wish_course_id (:wish_course_id user-set),
                 :wish_teacher (:wish_teacher user-set),
                 :entrance (:entrance user-set),
                 :entrance_day (:entrance_day user-set),
                 :class (:class user-set),
                 :department (:department user-set),
                 :graduate_course (:graduate_course user-set),
                 :laboratory_id (:laboratory_id user-set),
                 :student_id (:student_id user-set),
                 :guarantor_name (:guarantor_name user-set),
                 :guarantor_address (:guarantor_address user-set),
                 :guarantor_phone (:guarantor_phone user-set),
                 :finish_cource_day (:finish_cource_day user-set)}))

;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from user"]))

(insert {:name "t"})


