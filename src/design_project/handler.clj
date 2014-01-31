(ns design-project.handler
  (:use [compojure.core]
        [clojure.pprint]
        [ring.adapter.jetty])
  (:require [clojure.walk :as walk]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [ring.util.response :as resp]
            [net.cgrand.enlive-html :as enlive]
            [clojure.java.jdbc :as jdbc]
            [design-project.Models.database :as database]
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

(defn user-detail [user]
  (str "<!DOCTYPE html>\n<html lang=\"ja\">\n\t<head>\n\t\t<meta charset=\"UTF-8\">\n\t\t<meta name=\"keyword\" content=\"\">\n\t\t<meta name=\"description\" content=\"\">\n\t\t<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n\t\t\n\t\t<link href=\"/css/details.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\">\n\t\t<script src=\"/js/jquery-1.10.2.min.js\"></script>\n\t\t\n\t\t<script>\n\t\t</script>\n\t\t<title>学生詳細情報</title>\n\t</head>\n\t<body>\n\t    <div id=\"stuDetailMain\">\n\t\t<div id=\"modal-div\"></div>\n\t\t<button id='btn'>\n\t\t\t<div id=\"basisInfo\">\n\t\t\t\t<div id=\"stuInfo\">\n\t\t\t\t\t<p>基本情報</p>\n\t\t\t\t\t<p id=\"name\">氏名："(:name user)"</p>\n\t\t\t\t\t<p id=\"country\">国籍："(:country user)"</p>\n\t\t\t\t\t<p id=\"address\">住所："(:address user)"</p>\n\t\t\t\t\t<p id=\"phone\">電話番号："(:phone user)"</p>\n\t\t\t\t\t<p id=\"job_id\">職業："(:name_3 user)"</p>\n\t\t\t\t\t<p id=\"industry_id\">業種："(:name_4 user)"</p>\n\t\t\t\t\t<p id=\"birthday\">誕生日："(:birthday user)"</p>\n\t\t\t\t\t<p id=\"sex\">性別："(if (= 1 (:sex user)) "男" "女")"</p>\n\t\t\t\t</div><!-- #stuInfo end -->\n\t\t\t</div><!-- #basisInfo end -->\n\t\t\t<div id=\"compInfo\">\n\t\t\t\t<p>在学生情報</p>\n\t\t\t\t<p id=\"grade\">学年："(:class user)"</p>\n\t\t\t\t<p id=\"department_id\">学部："(:name_7 user)"</p>\n\t\t\t\t<p id=\"study_course_id\">課程："(:name_8 user)"</p>\n\t\t\t\t<p id=\"laboratory_id\">研究室："(:name_9 user)"</p>\n\t\t\t\t<p id=\"university_id\">大学："(:name_2 user)"</p>\n\t\t\t\t<p id=\"guarantor_name\">保証人："(:guarantor_name user)"</p>\n\t\t\t\t<p id=\"guarantor_address\">保証人住所："(:guarantor_address user)"</p>\n\t\t\t\t<p id=\"guarantor_phone\">保証人電話番号："(:guarantor_phone user)"</p>\n\t\t\t\t<p id=\"entrance_day\">入学日："(:entrance_day user)"</p>\n\t\t\t\t<p id=\"finish_course_day\">修了日："(:finish_course_day user)"</p>\n\t\t\t</div><!-- #compInfo end -->\n\t\t\t<div id=\"partInfo\">\n\t\t\t\t<p>参加者情報</p>\n\t\t\t\t<p id=\"wish_curriculum_id\">志望課程："(:name_5 user)"</p>\n\t\t\t\t<p id=\"wish_course_id\">志望コース："(:name_6 user)"</p>\n\t\t\t\t<p id=\"wish_teacher\">志望教官："(:wish_teacher user)"</p>\n\t\t\t\t\n\t\t\t\t<p id=\"entrance_day\">入学日："(:entrance_day user)"</p>\n\t\t\t</div><!-- #partInfo end -->\n\t\t\t<div id=\"stuEditButton\">\n\t\t\t\t<input type=\"submit\" value=\"削除\" class=\"editButtons\">\n\t\t\t\t<input type=\"submit\" value=\"編集\" class=\"editButtons\" onClick=\"location.href='/admin/student/edit'\">\n\t\t\t</div><!-- stuEditButton end -->\n\t\t</div><!-- #stuEditMain end -->\n\t</body>\n</html>\n"))

(def users (atom 
             (let [users (user/select-all)]
               (apply hash-map
                      (interleave (map :login_id users)
                                  (map transform-user users))))))

(defn f-1 [ds]
  (filter #(not= -1 (:id %)) ds))

(derive ::admin ::student)
(derive ::admin ::graduated)
(derive ::admin ::participants)

(defroutes app-routes
  (GET "/" req
       (let [roles (-> (friend/identity req) friend/current-authentication :roles)]
         (if (#{#{::admin} #{::student} #{::graduated} #{::participants}} roles)
           (resp/redirect (str (:context req)
                               (case (-> (friend/identity req) friend/current-authentication :roles)
                                 #{::admin} "/admin"
                                 #{::student} "/student"
                                 #{::graduated} "/graduated"
                                 #{::participants} "/participants"
                                 "/")))
           (resp/file-response "index.html" {:root "public/html"}))))
  (POST "/test" params
          (walk/keywordize-keys (:params params)))
            ;{:given-params ps
            ;:types (map (fn [[k v]]
            ;              (valid/keyword-to-type k)) ps)
            ;:valid-values
            ;(map (fn [[k v]]
            ;       ((valid/type-to-validator (valid/keyword-to-type k)) v))
            ;     ps)})))
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
  (GET "/admin/query" []
       (friend/authorize #{::admin}
                         (resp/file-response "query.html" {:root "public/html/admin"})))
  (POST "/admin/query" req
        (friend/authorize #{::admin}
                          (let [s (java.io.StringWriter.)]
                            (binding [*out* s]
                              (clojure.pprint/pprint
                                (jdbc/query database/my-db [(:query (walk/keywordize-keys (:params req)))])))
                            (.toString s))))
  (GET "/admin/event" []
       (friend/authorize #{::admin}
                         (resp/file-response "top.html" {:root "public/html/admin/event"})))
  (POST "/admin/event" req
        (friend/authorize #{::admin}
                          (json/generate-string (f-1 (event/select-all)))))
  (GET "/admin/event/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "add.html" {:root "public/html/admin/event"})))
  (POST "/admin/event/add" req
        (friend/authorize #{::admin}
                          (json/generate-string
                            {:status (not (nil? (event/insert (walk/keywordize-keys (:params req)))))})))
  (GET "/admin/event/detail" []
       (friend/authorize #{::admin}
                         (resp/file-response "detail.html" {:root "public/html/admin/event"})))
  (POST "/admin/event/detail" req
        (friend/authorize #{::admin}
                          (let [events (event/select-all)
                                id (Integer. (:id (walk/keywordize-keys (:params req))))]
                            (json/generate-string (f-1 (filter #(= (:id %) id)
                                                               events))))))
  (GET "/admin/student" []
       (friend/authorize #{::admin}
                         (resp/file-response "find.html" {:root "public/html/admin/student"})))
  (POST "/admin/student" req
        (friend/authorize #{::admin}
                          (json/generate-string (f-1 (user/select-all)))))
  (GET "/admin/student/find" []
       (friend/authorize #{::admin}
                         (resp/file-response "find.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/find" req
        (friend/authorize #{::admin}
                          (json/generate-string (f-1 (user/select-all)))))
  (GET "/admin/student/add" []
       (friend/authorize #{::admin}
                         (resp/file-response "add.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/add" req
        (friend/authorize #{::admin}
                          (let [input (walk/keywordize-keys (:params req))
                                status (not (nil? (user/insert input)))]
                            (when (true? status)
                              (reset! users (let [u (user/select-all)]
                                              (apply hash-map
                                                     (interleave (map :login_id u)
                                                                 (map transform-user u))))))
                            (json/generate-string  {:status status}))))
  (GET "/admin/student/detail" req
       (friend/authorize #{::admin}
                         (let [users (user/select-all)]
                           (user-detail
                             (first (filter #(= (Integer. 
                                                  (:id (walk/keywordize-keys (:params req)))) (:id %))
                                            users))))))
  (POST "/admin/student/detail" req
        (friend/authorize #{::admin}
                          (let [students (user/select-all)
                                id (Integer. (:id (walk/keywordize-keys (:params req))))]
                            (filter #(= (:id %) id)
                                    students))))
  (GET "/admin/student/edit" []
       (friend/authorize #{::admin}
                         (resp/file-response "edit.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/edit" req
        (friend/authorize #{::admin}
        (let [status (not (nil? (user/update (Integer. (:id (walk/keywordize-keys (:params req))))
                                             (walk/keywordize-keys (:params req)))))]
          (when (true? status)
            (reset! users (let [u (user/select-all)]
                            (apply hash-map
                                   (interleave (map :login_id u)
                                               (map transform-user u)))))))))
  (GET "/admin/student/delete" []
       (friend/authorize #{::admin}
                         (resp/file-response "delete.html" {:root "public/html/admin/student"})))
  (POST "/admin/student/delete" req
        (friend/authorize #{::admin}
        (json/generate-string {:status true})))

  (GET "/graduated" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated"})))
  (GET "/graduated/certificate" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/certificate"})))
  (POST "/graduated/certificate" req
        (friend/authorize #{::graduated}
                          (let [data (assoc (walk/keywordize-keys (:params req))
                                            :user_id
                                            (let [identity (friend/identity req)]
                                              (-> identity friend/current-authentication :user-id)))]
                            (if (not (nil? (certificate/insert data)))
                              "good"
                              (str "bad : " data)))))
  (GET "/graduated/event" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event" req
        (friend/authorize #{::graduated}
                          (json/generate-string (event/select-all))))
  (GET "/graduated/event/add" []
       (friend/authorize #{::graduated}
                         (resp/file-response "add.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/add" req
        (friend/authorize #{::graduated}
                          (json/generate-string
                            {:status (not (nil? (event/insert (walk/keywordize-keys (:params req)))))})))
  (GET "/graduated/event/detail" []
       (friend/authorize #{::graduated}
                         (resp/file-response "detail.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/detail" req
        (friend/authorize #{::graduated}
                          (let [events (event/select-all)
                                       id (Integer. (:id (walk/keywordize-keys (:params req))))
                                       event (filter #(= (:id %) id) events)
                                       identity (friend/identity req)
                                       user-id (-> identity friend/current-authentication :user-id)]
                            (event-read/insert {:user_id user-id :event_id id})
                            (json/generate-string event))))
  (GET "/graduated/event/history" []
       (friend/authorize #{::graduated}
                         (resp/file-response "history.html" {:root "public/html/graduated/event"})))
  (POST "/graduated/event/history" req
       (friend/authorize #{::graduated}
                         (let [identity (friend/identity req)]
                           (json/generate-string
                             (filter #(= (:user_id %) (-> identity friend/current-authentication :user-id))
                             (join-event-history/select-all))))))
  (GET "/graduated/profile" []
       (friend/authorize #{::graduated}
                         (resp/file-response "top.html" {:root "public/html/graduated/profile"})))
  (POST "/graduated/profile" req
        (friend/authorize #{::graduated}
                          (let [identity (friend/identity req)]
                            (json/generate-string (-> identity friend/current-authentication :all-info)))))
  (GET "/graduated/profile/edit" []
       (friend/authorize #{::graduated}
                         (resp/file-response "edit.html" {:root "public/html/graduated/profile"})))
  (POST "/graduated/profile/edit" req
        (friend/authorize #{::graduated}
                          (json/generate-string (event/update (Integer. (:id (:params req))) (:params req)))))
  (GET "/participants" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants"})))
  (POST "/participants" req
        (friend/authorize #{::participants}
                          (let [identity (friend/identity req)]
                            (json/generate-string (-> identity friend/current-authentication :all-info)))))
  (GET "/participants/event" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants/event"})))
  (POST "/participants/event" req
       (friend/authorize #{::participants}
                         (json/generate-string (filter #(= (:type_id %) 1) (f-1 (event/select-all))))))
  (GET "/participants/event/detail" []
       (friend/authorize #{::participants}
                         (resp/file-response "detail.html" {:root "public/html/participants/event"})))
  (GET "/participants/event/history" []
       (friend/authorize #{::participants}
                         (resp/file-response "history.html" {:root "public/html/participants/event"})))
  (POST "/participants/event/history" req
       (friend/authorize #{::participants}
                         (let [identity (friend/identity req)]
                           (json/generate-string
                             (filter #(= (:user_id %) (-> identity friend/current-authentication :user-id))
                             (join-event-history/select-all))))))

  (GET "/participants/profile" []
       (friend/authorize #{::participants}
                         (resp/file-response "top.html" {:root "public/html/participants/profile"})))
  (GET "/participants/profile/add" []
                         (resp/file-response "add.html" {:root "public/html/participants/profile"}))
  (POST "/participants/profile/add" req
                          (let [input (walk/keywordize-keys (:params req))
                                status (not (nil? (user/insert input)))]
                            (when (true? status)
                              (println @users)
                              (reset! users (let [u (user/select-all)]
                                              (apply hash-map
                                                     (interleave (map :login_id u)
                                                                 (map transform-user u))))))
                            (json/generate-string  {:status status})))
  (GET "/participants/profile/edit" []
       (friend/authorize #{::participants}
                         (resp/file-response "edit.html" {:root "public/html/participants/profile"})))
  (POST "/participants/profile/edit" req
        (friend/authorize #{::participants}
                          (let [input (walk/keywordize-keys (:params req))
                                identity (friend/identity req)]
                            (json/generate-string
                              (if (= 3 (Integer. (:status input)))
                                {:status (not (nil? (user/update (-> identity friend/current-authentication :user-id) input)))}
                                {:status false})))))
  (POST "/participants/event/participate" req
        (friend/authorize #{::participants}
                          (join-event-history/insert 
                            (walk/keywordize-keys 
                              (conj
                                (:params req)
                                {:user_id
                                (-> (friend/identity req)
                                  friend/current-authentication
                                  :user-id)})))
                          (resp/redirect (str (:context req) "/participants/event"))))
  (GET "/student" []
       (friend/authorize #{::student}
                         (resp/file-response "top.html" {:root "public/html/student/profile"})))
  (POST "/student" req
        (friend/authorize #{::student}
                          (let [profiles (user/select-all)
                                identity (friend/identity req)]
                            (json/generate-string (-> identity friend/current-authentication :all-info)))))
  (GET "/student/profile" []
       (friend/authorize #{::student}
                         (resp/file-response "top.html" {:root "public/html/student/profile"})))
  (POST "/student/profile" req
        (friend/authorize #{::student}
                          (let [profiles (user/select-all)
                                identity (friend/identity req)]
                            (json/generate-string (-> identity friend/current-authentication :all-info)))))
  (GET "/student/profile/edit" []
       (friend/authorize #{::student}
                         (resp/file-response "edit.html" {:root "public/html/student/profile"})))
  (POST "/student/profile/edit" req
        (friend/authorize #{::student}
                          (let [input (walk/keywordize-keys (:params req))
                                identity (friend/identity req)]
                            (json/generate-string
                              (if (< 0 (Integer. (:status input)))
                                {:status (not (nil? (user/update (-> identity friend/current-authentication :user-id) input)))}
                                {:status false})))))
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

(defn -main []
  (ring.adapter.jetty/run-jetty app {:port 8080}))
