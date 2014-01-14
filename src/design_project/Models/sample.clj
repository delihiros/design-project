(ns design-project.Models.sample
  (:use [design-project.Models.valid])
  (:require [design-project.Models.user :as user]))

(user/is-valid? {:university_id 1
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
         :entrance 1
         :entrance_day "2010-04-01"
         :class 2
         :department_id 1
         :study_course_id 9
         :laboratory_id 1
         :student_id "123"
         :guarantor_name "保証人"
         :guarantor_address "保証人住所"
         :guarantor_phone "1234"
         :finish_course_day "2010-01-01"})
;; student user
(user/insert {:university_id 1
         :status 1
         :name "student"
         :login_id "student1"
         :password "student"
         :country "日本"
         :address "住所"
         :phone "123456789"
         :job_id 9
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
         :finish_course_day "2010-01-01"})

;; event entry user
(user/insert {:university_id 1
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
         :entrance 1})

;; graduated user
(user/insert {:university_id 1
         :status 3
         :name "graduated"
         :login_id "graduated-1"
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

(let [input {:status "0",
            :country "1",
            :job_id "1",
            :name "ねーむ",
            :login_id "yurippe",
            :sex "1",
            :phone "080-5238-2593",
            :industry_id "1",
            :address "にほん",
;            :id "11",
            :password "yurippe",
            :birthday "1992-12-12"}]
  (valid-values? input))
