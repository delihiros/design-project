(ns design-project.Models.event
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
;; 日時
;; 場所
;; イベント種類
(defn insert [event-set]
  (jdbc/insert! my-db :event
                {:day (:day event-set),
                 :place (:place event-set),
                 :type_id (:type_id event-set)}))

;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from event"]))


