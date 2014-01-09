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
    :roles #{::student}}}))

(defroutes app-routes
  (GET "/" []
       (resp/file-response "index.html" {:root "src/design_project/views/html"}))
  (POST "/login" params
        (str params))
  (GET "/logout" req
       (friend/logout* (ring.util.response/redirect (str (:context req) "/"))))
  (GET "/status" req
       (if-let [identity (friend/identity req)]
         (apply str "Logged in, with these roles: "
                (-> identity friend/current-authentication )) ; :roles))
         "anonymous user"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site
    (friend/authenticate
      app-routes
      {:allow-anon? true
      :login-uri "/login"
      :default-landing-url "/"
      :unauthorized-handler (str "unauthorized!!!")
      :credential-fn #(creds/bcrypt-credential-fn @users %)
      :workflows [(workflows/interactive-form)]})))
