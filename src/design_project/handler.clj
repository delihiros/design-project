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
            [design-project.Models.valid :as valid]
            [cheshire.core :as json]))

(defn transform-user [user]
  {:username (:login_id user)
  :roles #{(case (:status user)
             -1 ::noboby
             0  ::admin
             1  ::graduated
             2  ::student
             3  ::participants)}
  :password (creds/hash-bcrypt (:password user))
  :user-id (:id user)
  :all-info user
  ;:others user
  })

(def users (atom 
             (let [users (user/select-all)]
               (apply hash-map
                      (interleave (map :login_id users)
                                  (map transform-user users))))))

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
  (POST "/test" params
    (json/generate-string (walk/keywordize-keys (:params params))))
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
  (POST "/admin/event" req
        (json/generate-string (event/select-all)))
  (GET "/admin/event/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "add.html" {:root "public/html/admin/event"})))
  (POST "/admin/event/add" req
        (json/generate-string
          {:status (not (nil? (event/insert (walk/keywordize-keys (:params req)))))}))
  (GET "/admin/event/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "detail.html" {:root "public/html/admin/event"})))
  (POST "/admin/event/detail" req
        (let [events (event/select-all)
              id (Integer. (:id (walk/keywordize-keys (:multipart-params req))))]
          (json/generate-string (filter #(= (:id %) id)
                                        events))))
  (GET "/admin/student" []
       (friend/authorize #{::admin}
                         (resp/file-response "find.html" {:root "public/html/admin/student"})))
  (POST "/admin/student" req
        (json/generate-string (user/select-all)))
  (GET "/admin/student/find" []
       (friend/authorize #{::admin}
                         (resp/file-response "find.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/find" req
        (json/generate-string (user/select-all)))
  (GET "/admin/student/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "add.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/add" req
        (let [input (walk/keywordize-keys (:multipart-params req))
                    status (not (nil? (user/insert input)))]
          (when (true? status)
            (reset! users (let [u (user/select-all)]
                           (apply hash-map
                                  (interleave (map :login_id u)
                                              (map transform-user u))))))
          (json/generate-string  {:status status})))
  (GET "/admin/student/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "detail.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/detail" req
        (let [students (doall (user/select-all))
                       id (Integer. (:id (walk/keywordize-keys (:multipart-params req))))]
          (json/generate-string (filter #(= (:id %) id)
                                        students))))
  (GET "/admin/student/edit" []
       (friend/authorize #{::admin}
                         (resp/file-response "edit.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/edit" req
        (println (walk/keywordize-keys (:multipart-params req)))
        (user/update (Integer. (:id (walk/keywordize-keys (:multipart-params req))))
        (walk/keywordize-keys (:multipart-params req))))
  (GET "/admin/student/delete" []
       (friend/authorize #{::admin}
                         (resp/file-response "delete.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/delete" req
        (json/generate-string {:status true}))

  (GET "/graduated" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated"})))
  (GET "/graduated/certificate" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/certificate"})))
  (POST "/graduated/certificate" req
        (json/generate-string
          {:status (not (nil? (event/insert (walk/keywordize-keys (:multipart-params req)))))}))
  (GET "/graduated/event" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event" req
        (json/generate-string (event/select-all)))
  (GET "/graduated/event/add" []
       (friend/authorize #{::graduated}
                         (resp/file-response "add.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/add" req
        (json/generate-string
          {:status (not (nil? (event/insert (walk/keywordize-keys (:multipart-params req)))))}))
  (GET "/graduated/event/detail" []
       (friend/authorize #{::graduated}
                         (resp/file-response "detail.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/detail" req
        (let [events (doall (event/select-all))
                     id (Integer. (:id (walk/keywordize-keys (:multipart-params req))))]
          (json/generate-string (filter #(= (:id %) id)
                                        events))))
  (GET "/graduated/profile" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/profile"})))
  (POST "/graduated/profile" req
        (let [profiles (user/select-all)
              identity (friend/identity req)]
          (json/generate-string (-> identity friend/current-authentication :all-info))))
  (GET "/graduated/profile/edit" []
       (friend/authorize #{::graduated}
                         (resp/file-response "edit.html" {:root "public/html/graduated/profile"})))
  (POST "/graduated/profile/edit" req
        (json/generate-string (event/update (Integer. (:id (:multipart-params req))) (:multipart-params req))))
  (GET "/participants" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants"})))
  (POST "/participants" req
        (let [profiles (user/select-all)
              identity (friend/identity req)]
          (json/generate-string (-> identity friend/current-authentication :all-info))))
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
       (resp/file-response "add.html" {:root "public/html/participants/profile"}))
  (POST "/participants/profile/add" req
        (let [input (walk/keywordize-keys (:multipart-params req))]
          (json/generate-string
            (if (= 3 (Integer. (:status input)))
              {:status (not (nil? (user/insert input)))}
              {:status false}))))
  (GET "/participants/profile/edit" []
       (friend/authorize #{::participants}
                         (resp/file-response "edit.html" {:root "public/html/participants/profile"})))
  (POST "/participants/profile/edit" req
        (let [input (walk/keywordize-keys (:multipart-params req))
                    identity (friend/identity req)]
          (json/generate-string
            (if (= 3 (Integer. (:status input)))
              {:status (not (nil? (user/update (-> identity friend/current-authentication :user-id) input)))}
              {:status false}))))

  (GET "/student" []
       (friend/authorize #{::student}
                         (resp/file-response "top.html" {:root "public/html/student"})))
  (POST "/student" req
        (let [profiles (user/select-all)
              identity (friend/identity req)]
          (json/generate-string (-> identity friend/current-authentication :all-info))))
  (GET "/student/profile" []
       (friend/authorize #{::student}
                         (resp/file-response "top.html" {:root "public/html/student/profile"})))
  (POST "/student/profile" req
        (let [profiles (user/select-all)
              identity (friend/identity req)]
          (json/generate-string (-> identity friend/current-authentication :all-info))))
  (GET "/student/profile/edit" []
       (friend/authorize #{::student}
                         (resp/file-response "edit.html" {:root "public/html/student/profile"})))
  (POST "/student/profile/edit" req
        (let [input (walk/keywordize-keys (:multipart-params req))
                    identity (friend/identity req)]
          (json/generate-string
            (if (< 0 (Integer. (:status input)))
              {:status (not (nil? (user/update (-> identity friend/current-authentication :user-id) input)))}
              {:status false}))))


  (POST "/event/participate" req
        (json/generate-string 
          {:status (not (nil? (join-event-history/insert (walk/keywordize-keys (:multipart-params req)))))}))
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
