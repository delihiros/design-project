(ns design-project.Models.event-read
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
;; ユーザーid
;; イベントid
(defn insert [evet-read-set]
  (jdbc/insert! my-db :event_read
                {:user_id (:user_id event-read-set),
                 :event_id (:event_id event-read-set)}))

;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from event_read"]))


