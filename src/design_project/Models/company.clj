(ns design-project.Models.company
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]
            [hiccup.util :as h]))

;; ちゃんと値のチェックもする

;;  Listで
(def company-data (agent (select)))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-company-data
  "add company data in list.
  when add list, inclement id
  return
  list in company data."
  [com id]
  (send company-data conj (assoc com :id id)))

(defn is-valid? [n]
  (and (not (nil? n)) (< 0 (count  n) 64)))

;; insert
(defn insert 
  "insert company table.
  parameter
   com-map attribute 
    :name company name.
  return 
   generate id"
  [{name :name}]
  
  (if (is-valid? (h/escape-html name))
    (add-company-data {:name name}
                      (:generated_key 
                        (first 
                          (jdbc/insert! my-db :company {:name name}))))))

;; select
;; filter かけれるようにする予定
(defn select 
  "select from company table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from company"]))


(comment
  ;; sample
  (insert {:name "ab" :hoge "foo"}))
  (select)
