(ns design-project.Models.valid
  (:use [clojure.set])
  (:require [hiccup.util :as h]))

(def keyword-to-type
  { :address :VARCHAR,
   :addressee :TEXT,
   :birthday :DATE,
   :certificate_type :INT,
   :class :INT,
   :comment :TEXT,
   :company_id :INT,
   :completion_year :DATE,
   :country :VARCHAR,
   :day :DATE,
   :department_id :INT,
   :entrance_day :DATE,
   :entrance :BOOLEAN,
   :event_id :INT,
   :finish_course_day :DATE,
   :guarantor_address :VARCHAR,
   :guarantor_name :VARCHAR,
   :guarantor_phone :VARCHAR,
   :hash_method :VARCHAR,
   :id :INT,
   :image :TEXT,
   :industry_id :INT,
   :job_id :INT,
   :laboratory_id :INT,
   :leaves :INT,
   :login_id :VARCHAR,
   :name :VARCHAR,
   :password :VARCHAR,
   :phone :VARCHAR,
   :place :VARCHAR,
   :position :VARCHAR,
   :purpose :TEXT,
   :sex :INT,
   :status :INT,
   :student_id :VARCHAR,
   :study_course_id :INT,
   :type_id :INT,
   :university_id :INT,
   :uptime :DATETIME,
   :user_id :INT,
   :wish_course_id :INT,
   :wish_curriculum_id :INT,
   :wish_teacher :VARCHAR
   })

(def type-to-validator
  {:INT #(if (string? %) (every? #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9} %) (integer? %))
   :TEXT #(string? %)
   :VARCHAR #(and (string? %) (< (count %) 256))
   :DATE #(re-seq #"\d\d\d\d\-\d\d\-\d\d" %)
   })

(defn row-exist?
  "return whether row exist"
  [keywords]
  (fn [input]
    (subset? (set (keys input)) (set keywords))))

(defn not-null? [keywords]
  (fn [input]
    (subset? (set keywords) (set (keys input)))))

(defn valid-values? [input]
  (every? true? 
          (map (fn [[k v]]
                 ((type-to-validator (keyword-to-type k)) v))
               input)))
