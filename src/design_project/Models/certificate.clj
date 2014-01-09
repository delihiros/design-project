(ns design-project.Models.certificate
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; ちゃんと値のチェックもする

;;  Listで
(def certificate-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-certificate-data
  "add certificate data in list.
  when add list, inclement id
  return
  list in certificate data."
  [com id]
  (send certificate-data conj (assoc com :id id)))


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
  (add-certificate-data com-map 
                    (:generated_key 
                      (first 
                        (jdbc/insert! my-db :certificate com-map)))))

;; select
;; filter かけれるようにする予定
(defn select 
  "select from certificate table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from certificate, user 
               where certificate.user_id = user.id"]))

