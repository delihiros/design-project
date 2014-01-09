(ns design-project.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [ring.util.response :as resp]))

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
  (GET "/" []
       (resp/file-response "index.html" {:root "src/design_project/views/html"}))
  (POST "/login" params
        (str params))
  (GET "/logout" req
       (friend/logout*  (resp/redirect (str (:context req) "/"))))
  (POST "/logout" req
        (friend/logout* (resp/redirect (str (:context req) "/"))))
  (GET "/status" req
       (if-let [identity (friend/identity req)]
         (apply str "Logged in, with these roles: "
                (-> identity friend/current-authentication )) ; :roles))
         "anonymous user"))
  (GET "/admin" []
       (friend/authorize #{::admin}
                         (resp/file-response "schooltop.html" {:root "src/design_project/views/html"})))
  (GET "/admin/student" []
       (friend/authorize #{::admin}
                         (resp/file-response "students.html" {:root "src/design_project/views/html"})))
  (GET "/admin/student/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "studetails.html" {:root "src/design_project/views/html"})))
  (GET "/admin/student/delete" []
       (friend/authorize #{::admin}
                         (resp/file-response "studelete.html" {:root "src/design_project/views/html"})))
  (GET "/admin/student/entry" []
       (friend/authorize #{::admin}
                         (resp/file-response "stunewentry.html" {:root "src/design_project/views/html"})))
  (GET "/admin/event" []
       (friend/authorize #{::admin}
                         (resp/file-response "event.html" {:root "src/design_project/views/html"})))
  (GET "/admin/event/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "evedetails.html" {:root "src/design_project/views/html"})))
  (GET "/admin/event/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "evenewentry.html" {:root "src/design_project/views/html"})))
  (GET "/student" []
       (friend/authorize #{::student}
                         (resp/file-response "Stutop.html" {:root "src/design_project/views/html"})))
  (GET "/student/profile" []
       (friend/authorize #{::student}
                         (resp/file-response "profile.html" {:root "src/design_project/views/html"})))
  (GET "/student/profile/edit" []
       (friend/authorize #{::student}
                         (resp/file-response "profileedit.html" {:root "src/design_project/views/html"})))
  (GET "/student/event" []
       (friend/authorize #{::student}
                         (resp/file-response "Stueventdetail.html" {:root "src/design_project/views/html"})))
  (GET "/graduated/event/add" []
       (friend/authorize #{::graduated}
                         (resp/file-response "addevent.html" {:root "src/design_project/views/html"})))
  (GET "/graduated/certificate" []
       (friend/authorize #{::graduated}
                         (resp/file-response "syomeisyo.html" {:root "src/design_project/views/html"})))
  (POST "/graduated/certificate" req
        (friend/authorize #{::graduated}
                          (do
                          (println (str "certificate requested" req))
                            "hello, world")))
  (route/resources "src/design_project/views/html")
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
