(ns design-project.Models.certificate
  (:use [design-project.Models.database]
        [design-project.Models.valid])
  (:require [clojure.java.jdbc :as jdbc]))

;; select
(defn select-all
  "select from certificate table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from certificate, user 
               where certificate.user_id = user.id"]))


;;  Listで
(def certificate-data (agent (select-all)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-certificate-data
  "add certificate data in list.
  when add list, inclement id
  return
  list in certificate data."
  [com id]
  (send certificate-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((row-exist? [:id :user_id :certificate_type :completion_year :leaves :purpose :addressee]) input)
       ((not-null? [:user_id :certificate_type]) input)
       (valid-values? input)
       (foreign-key-exist? :user {:id (:user_id input)})))

;; insert
(defn insert 
  "insert certificate table.
  parameter
   certificate-map attribute 
    user_id 
    certificate_type 1:Bachelor 2:Doctorfirst 3:Doctorsecond
    completion_year 
    leaves
    purpose 
    addressee
  return 
   certificate-data list"
  [com-map]
  (if (is-valid? com-map) 
    (add-certificate-data com-map 
                          (:generated_key 
                            (first 
                              (jdbc/insert! my-db :certificate com-map))))))

(comment
  ;; sample 
  (insert {:user_id 1 
          :certificate_type 1
          :completion_year "2013-03-31"
          :leaves 1
          :purpose "出して！！"
          :addressee "住所"} )

  (select-all))
