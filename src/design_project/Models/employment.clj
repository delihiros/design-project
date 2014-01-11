(ns design-project.Models.employment
  (:use [design-project.Models.database])

;; ちゃんと値のチェックもする 

;;  Listで
(def employment-data (agent ()))

;; onMemoryで管理するためのリストにデータを追加する
(defn add-employment-data
  "add employment data in list.
  when add list, inclement id return
  list in employment data."
  [com id]
  (send employment-data conj (assoc com :id id)))



;; insert
;; id
;; ユーザーid
;; 会社id
;; 役職
;; 業種id
;; その他特記事項
;; 変更時間
(defn insert 
  "insert employment table.
  parameter
   employment-map attribute
   :user_id foreign key references user table.
   :company_id foreign key references company table.
   :position 役職
   :industry_id 業種
   :comment 
   :uptime 
 return
  generate id" 
  [employment-map]
  (add-employment-data employment-map
                       (:generated_key
                         (first
                           (jdbc/insert! my-db :employment employment-map)))))

;; update
(defn update 
  "update employment table.
  parameter
   want update id and change attribute.
  return
   1 is accept
   exception is fail"
  [id employment-map]
  (jdbc/update! my-db :employment employment-map ["id=?" id]))

;; select
(defn select 
  "select from employment table.
  return
   select data in map."
  []
  (jdbc/query my-db
              ["select * from employment, user, company
                where employment.user_id = user.id,
                and employment.company_id = company.id"]))

;; sample


(insert {:user_id 4
         :company_id 1
         :position "シャチョ"
         :industry_id 1
         :comment "コメントあれば"
         :uptime  "2013-01-01"})
