(ns design-project.Models.company
  (:use [design-project.Models.database]
        [design-project.Models.valid])
  (:require [clojure.java.jdbc :as jdbc]
            [hiccup.util :as h]))

;; ちゃんと値のチェックもする

;; select
(defn select-all
  "select from company table.
  return
  select data in map."
  []
  (jdbc/query my-db
              ["select * from company"]))

;; filter かけれるようにする予定
(defn from-key
  "select from company table.
  return
  select data in map."
  [id]
  (jdbc/with-connection 
    my-db
    (jdbc/with-query-results 
      rows
      ["select * from company where id=?" id]
      (doall rows))))

;;  Listで
(def company-data (agent (select-all)))
;; onMemoryで管理するためのリストにデータを追加する
(defn add-company-data
  "add company data in list.
  when add list, inclement id
  return
  list in company data."
  [com id]
  (send company-data conj (assoc com :id id)))

(defn is-valid? [input]
  (and ((valid-keys? [:name]) input)
       (every? true? (map (fn [[k v]]
                            ((type-to-validator (keyword-to-type k)) v))
                          input))))

;; insert
(defn insert 
  "insert company table.
  parameter
  com-map attribute 
  :name company name.
  return 
  generate id"
  [input]

  (if (is-valid? input)
    (add-company-data input
                      (:generated_key 
                        (first 
                          (jdbc/insert! my-db :company input))))))


(comment
  ;; sample
  (insert {:name "ab" :hoge "foo"})
  (insert {:id "10" :name "ab"})
  (select-all)
)
