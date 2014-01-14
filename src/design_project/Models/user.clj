(ns design-project.Models.user
  (:use [design-project.Models.database]
        [design-project.Models.valid]
        [clojure.set])
  (:require [clojure.java.jdbc :as jdbc]
            [hiccup.util :as h]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする
(defn select-all
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

(def user-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-user-data
  "add user data in list.
  when add list, inclement id
  return
  list in user data."
  [com id]
  (send user-data conj (assoc com :id id)))


;; validator
(defn is-valid? [input]
  (and ((not-null? [:status :name :login_id :password :country :address :phone :job_id :industry_id :birthday :sex]) input)
       ((row-exist? [:guarantor_name :status :country :department_id :guarantor_phone :job_id :entrance_day :class :entrance :name :login_id :sex :university_id :guarantor_address :phone :industry_id :wish_curriculum_id :student_id :wish_teacher :study_course_id :address :password :laboratory_id :birthday :wish_course_id :finish_course_day]) input)
       (valid-values? input)))
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
  :login_id
  :password
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
  [input]
  (if (is-valid? input) 
    (add-user-data input 
                   (:generated_key
                     (first
                       (jdbc/insert! my-db :user input)))))

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
(comment
  ;; sample
  ;; admin user
  (insert {:university_id 1
           :status 0
           :name "admin1"
           :login_id "admin"
           :password "admin"
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
           :login_id "student1"
           :password "student"
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
           :login_id "entry1"
           :password "entry"
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
           :login_id "graduated"
           :password "graduated"
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

(comment
  (:guarantor_name :status :country :class :department_id :guarantor_phone :job_id :name :image :login_id :sex :hash_method :university_id :guarantor_address :phone :industry_id :wish_curriculum_id :wish_teacher :study_course_id :address :password :birthday :wish_course_id :finish_course_day) 
  (keys 
    {:university_id :university_id
     :status :status
     :image :image
     :name :name
     :login_id :login_id
     :password :password
     :hash_method :hash_method
     :country :country
     :address :address
     :phone :phone
     :job_id :job_id
     :industry_id :industry_id
     :birthday :birthday
     :sex :sex
     :wish_curriculum_id :wish_curriculum_id
     :wish_course_id :wish_course_id
     :wish_teacher :wish_teacher
     :cls :class
     :department_id :department_id
     :study_course_id :study_course_id
     :guarantor_name :guarantor_name
     :guarantor_address :guarantor_address
     :guarantor_phone :guarantor_phone
     :finish_course_day :finish_course_day}) )
(+ 1 1)
(is-valid? {:university_id 1
            :status 3
            :image "hogehoge"
            :name "graduated"
            :login_id "graduated"
            :password "graduated"
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
            :entrance 1 
            :entrance_day "2010-04-01"
            :class 2
            :department_id 1
            :study_course_id 1
            :laboratory_id 1
            :student_id "123"
            :guarantor_name "保証人"
            :guarantor_address "保証人住所"
            :guarantor_phone "1234"
            :finish_course_day "2016-03-31"})

((row-exist? [:guarantor_name :status :country :department_id :guarantor_phone :job_id :entrance_day :class :entrance :name :login_id :sex :university_id :guarantor_address :phone :industry_id :wish_curriculum_id :student_id :wish_teacher :study_course_id :address :password :laboratory_id :birthday :wish_course_id :finish_course_day])
   {:university_id 1
            :status 3
            :name "graduated"
            :login_id "graduated"
            :password "graduated"
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
            :entrance 1 
            :entrance_day "2010-04-01"
            :class 2
            :department_id 1
            :study_course_id 1
            :laboratory_id 1
            :student_id "123"
            :guarantor_name "保証人"
            :guarantor_address "保証人住所"
            :guarantor_phone "1234"
            :finish_course_day "2016-03-31"})

