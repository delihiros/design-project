(ns design-project.Models.join-event-history
  (:use [design-project.Models.database])
  (:require [clojure.java.jdbc :as jdbc]))

;; エージェントを使ってオンメモリで
;; 欲しいデータを取り出しやすくする
;; ちゃんと値のチェックもする


;; insert
(defn insert [histroy-set]
  (jdbc/insert! my-db :join_event_history
                {:event_id (:event_id history-set)
                 :user_id (:user_id history-set)}))

;; select
(defn db-select []
  (jdbc/query my-db
              ["select * from join_event_history"]))


