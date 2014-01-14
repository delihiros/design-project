(ns design-project.Models.valid
  (:use [clojure.set]
        [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]
            [hiccup.util :as h]))

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
   :entrance :INT,
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
   :DATE #(not (empty? (re-seq #"^\d\d\d\d\-\d\d\-\d\d$" %)))
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

(defn foreign-key-exist?
  "
  (foreign-key-exist? :company {:id 1})
  "
  [table ids]
  (let [table-name (apply str (rest (str table)))
        row-name (apply str (rest (str (first (keys ids)))))
        row-value (first (vals ids))]
    (not (zero? (count (jdbc/with-connection
                         my-db
                         (jdbc/with-query-results 
                           rows
                           [(str "select * from " table-name " where " row-name "=?") row-value]
                           (doall rows))))))))

