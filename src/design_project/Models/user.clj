(ns design-project.Models.user
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする

(def user-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-user-data
  "add user data in list.
  when add list, inclement id
  return
  list in user data."
  [com id]
  (send user-data conj (assoc com :id id)))

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
  (add-user-data user-map
                 (:generated_key
                   (first
                     (jdbc/insert! my-db :user user-map)))))

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
              ["select * from user, university, job, 
               industry_type, curriculum, course, department,
               study, lab
               where user.university_id = university.id
               and user.job_id = job.id
               and user.industry_id = industry_type.id
               and user.wish_curriculum_id = curriculum.id
               and user.wish_course_id = course.id
               and user.department_id = department.id
               and user.study_course_id = study.id
               and user.laboratory_id = lab.id"]))

(comment
  ;; sample
  ;; admin user
  (insert {:university_id 1
          :status 0
          :name "admin"
          :password "admin"
          :hash_method "admin"
          :country "日本"
          :address "住所"
          :phone "123456789"
          :job_id 1
          :industry_id 1
          :birthday "1232-09-09"
          :sex 1})


  ;; student user
  (insert {:university_id 1
          :status 1
          :name "student"
          :password "student"
          :hash_method "student"
          :country "日本"
          :address "住所"
          :phone "123456789"
          :job_id 1
          :industry_id 1
          :birthday "2123-09-09"
          :sex 1
          :wish_curriculum_id 1
          :wish_course_id 1
          :wish_teacher "先生"
          :entrance true
          :entrance_day "2010-04-01"
          :class 2
          :department_id 1
          :study_course_id 1
          :laboratory_id 1
          :student_id "123"
          :guarantor_name "保証人"
          :guarantor_address "保証人住所"
          :guarantor_phone "1234"
          :finish_course_day false})

  ;; event entry user
  (insert {:university_id 1
          :status 2
          :name "entry"
          :password "entry"
          :hash_method "entry"
          :country "日本"
          :address "住所"
          :phone "123456789"
          :job_id 1
          :industry_id 1
          :birthday "1232-09-09"
          :sex 1
          :entrance false})

  ;; graduated user
  (insert {:university_id 1
          :status 3
          :name "graduated"
          :password "graduated"
          :hash_method "graduated"
          :country "日本"
          :address "住所"
          :phone "123456789"
          :job_id 1
          :industry_id 1
          :birthday "2123-09-09"
          :sex 1
          :wish_curriculum_id 1
          :wish_course_id 1
          :wish_teacher "先生"
          :entrance true
          :entrance_day "2010-04-01"
          :class 2
          :department_id 1
          :study_course_id 1
          :laboratory_id 1
          :student_id "123"
          :guarantor_name "保証人"
          :guarantor_address "保証人住所"
          :guarantor_phone "1234"
          :finish_course_day "2016-03-31"}))
