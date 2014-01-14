(ns design-project.handler
  (:use [compojure.core])
  (:require [clojure.walk :as walk]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [ring.util.response :as resp]
            [design-project.Models.certificate :as certificate]
            [design-project.Models.company :as company]
            [design-project.Models.course :as course]
            [design-project.Models.curriculum :as curriculum]
            [design-project.Models.department :as department]
            [design-project.Models.employment :as employment]
            [design-project.Models.event :as event]
            [design-project.Models.event-read :as event-read]
            [design-project.Models.event-type :as event-type]
            [design-project.Models.industry-type :as industry-type]
            [design-project.Models.join-event-history  :as join-event-history]
            [design-project.Models.lab :as lab]
            [design-project.Models.study :as study]
            [design-project.Models.university :as university]
            [design-project.Models.user :as user]
            [design-project.Models.valid :as valid]))

(defn transform-user [user]
  {:username (:login_id user)
  :roles #{(case (:status user)
             -1 ::noboby
             0  ::admin
             1  ::graduated
             2  ::student
             3  ::participants)}
  :password (creds/hash-bcrypt (:password user))
  :pin "1234"
  :others user})

(map transform-user (user/select-all))
(def users (atom (hash-map (interleave 
                   (iterate inc 0)
                   (map transform-user (user/select-all))))))

{:username "delihiros"}


(derive ::admin ::student)
(derive ::admin ::graduated)
(derive ::admin ::participants)

(defroutes app-routes
  (GET "/" req
       (let [roles (-> (friend/identity req) friend/current-authentication :roles)]
         (if (#{#{::admin} #{::student} #{::graduated}} roles)
           (resp/redirect (str (:context req)
                               (case (-> (friend/identity req) friend/current-authentication :roles)
                                 #{::admin} "/admin"
                                 #{::student} "/student"
                                 #{::graduated} "/graduated"
                                 "/")))
           (resp/file-response "index.html" {:root "public/html"}))))
  (POST "/login" params
        params)
  (GET "/logout" req
       (friend/logout*  (resp/redirect (str (:context req) "/"))))
  (POST "/logout" req
        (friend/logout* (resp/redirect (str (:context req) "/"))))
  (GET "/status" req
       (if-let [identity (friend/identity req)]
         (apply str "Logged in, with these roles: "
                (-> identity friend/current-authentication )) ; :roles))
         "<h3>you are an anonymous</h3>"))

  (GET "/admin" []
       (friend/authorize #{::admin}
                         (resp/file-response "top.html" {:root "public/html/admin"})))
  (GET "/admin/event" []
       (friend/authorize #{::admin}
                         (resp/file-response "top.html" {:root "public/html/admin/event"})))
  (GET "/admin/event/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "add.html" {:root "public/html/admin/event"})))
  (POST "/admin/event/add" req
        (do 
          (event/insert (walk/keywordize-keys (:multipart-params req)))
          (walk/keywordize-keys (:multipart-params req))))
  (GET "/admin/event/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "detail.html" {:root "public/html/admin/event"})))
  (GET "/admin/student" []
       (friend/authorize #{::admin}
                         (resp/file-response "find.html" {:root "public/html/admin/student"})))
  (GET "/admin/student/find" []
       (friend/authorize #{::admin}
                         (resp/file-response "find.html" {:root "public/html/admin/student"})))
  (GET "/admin/student/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "add.html" {:root "public/html/admin/student"})))
  (GET "/test" []
       (reduce str (user/select-all)))
  (POST "/admin/student/add" req
          (user/insert (walk/keywordize-keys (:multipart-params req))))
  (GET "/admin/student/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "detail.html" {:root "public/html/admin/student"})))
  (GET "/admin/student/edit" []
       (friend/authorize #{::admin}
                         (resp/file-response "edit.html" {:root "public/html/admin/student"})))
  (GET "/admin/student/delete" []
       (friend/authorize #{::admin}
                         (resp/file-response "delete.html" {:root "public/html/admin/student"})))


  (GET "/graduated" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated"})))
  (GET "/graduated/certificate" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/certificate"})))
  (POST "/graduated/certificate" req
        (friend/authorize #{::graduated}
                          (do
                            (println (str "certificate requested" req))
                            "hello, world")))
  (GET "/graduated/event" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/event"})))
  (GET "/graduated/event/add" []
       (friend/authorize #{::graduated}
                         (resp/file-response "add.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/add" req
        (event/insert (walk/keywordize-keys (:multipart-params req))))

  (GET "/graduated/event/detail" []
       (friend/authorize #{::graduated}
                         (resp/file-response "detail.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/detail" req
        (let [events (doall (event/select-all))
                     id (Integer. (:id (walk/keywordize-keys (:multipart-params req))))]
          (filter #(= (:id %) id)
                  events)))
  (GET "/graduated/profile" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/profile"})))
  (POST "/graduated/profile" req
        (let [profiles (user/select-all)
              identity (friend/identity req)]
          (-> identity friend/current-authentication)))
  (GET "/graduated/profile/edit" []
       (friend/authorize #{::graduated}
                         (resp/file-response "edit.html" {:root "public/html/graduated/profile"})))

  (GET "/participants" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants"})))
  (GET "/participants/event" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants/event"})))
  (GET "/participants/event/detail" []
       (friend/authorize #{::participants}
                         (resp/file-response "detail.html" {:root "public/html/participants/event"})))
  (GET "/participants/event/history" []
       (friend/authorize #{::participants}
                         (resp/file-response "history.html" {:root "public/html/participants/event"})))
  (GET "/participants/profile" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants/profile"})))
  (GET "/participants/profile/add" []
       (friend/authorize #{::participants}
                         (resp/file-response "add.html" {:root "public/html/participants/profile"})))
  (GET "/participants/profile/edit" []
       (friend/authorize #{::participants}
                         (resp/file-response "edit.html" {:root "public/html/participants/profile"})))

  (GET "/student" []
       (friend/authorize #{::student}
                         (resp/file-response "top.html" {:root "public/html/student"})))
  (GET "/student/profile" []
       (friend/authorize #{::student}
                         (resp/file-response "top.html" {:root "public/html/student/profile"})))
  (GET "/student/profile/edit" []
       (friend/authorize #{::student}
                         (resp/file-response "edit.html" {:root "public/html/student/profile"})))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site
    (friend/authenticate
      app-routes
      {:allow-anon? true
      :login-uri "/login"
      :default-landing-url "/"
      :unauthorized-handler (fn [_] (resp/response "unauthorized!!!"))
      :credential-fn #(creds/bcrypt-credential-fn @users %)
      :workflows [(workflows/interactive-form)]})))
