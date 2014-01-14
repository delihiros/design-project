(ns design-project.Models.database)
;; database
;; ユーザー名とパスワードは各々の環境にあわせて変更する
;;
(def my-db {:subprotocol "mysql"
            :subname "//127.0.0.1:3306/t?zeroDateTimeBehavior=convertToNull"
            :user "user"
            :password "password"})


