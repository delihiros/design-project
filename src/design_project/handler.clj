(ns design-project.handler
  (:use [compojure.core])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [ring.util.response :as resp]
            [design-project.Models.event :as event]))

(def users
  (atom
    {"delihiros"
    {:username "delihiros" 
    :password (creds/hash-bcrypt "delihiros")
    :pin "1234"
    :roles #{::admin}}
    "student"
    {:username "student"
    :password (creds/hash-bcrypt "student")
    :pin "1234"
    :roles #{::student}}
    "graduated"
    {:username "graduated" 
    :password (creds/hash-bcrypt "graduated")
    :pin "1234"
    :roles #{::graduated}}}))

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
          (event/insert (:multipart-params req))
          (str (:multipart-params req))))
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
  (GET "/admin/student/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "detail.html" {:root "public/html/admin/student"})))
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
  (GET "/graduated/event/detail" []
       (friend/authorize #{::graduated}
                         (resp/file-response "detail.html" {:root "public/html/graduated/event"})))
  (GET "/graduated/profile" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/profile"})))
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
