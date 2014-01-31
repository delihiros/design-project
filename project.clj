(defproject design-project "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [hiccup "1.0.4"]
                 [sandbar/sandbar "0.4.0-SNAPSHOT"]
                 [org.clojure/java.jdbc "0.3.0-beta1"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [com.cemerick/friend "0.2.0"]
                 [clj-time "0.6.0"]
                 [cheshire "4.0.2"]
                 [enlive "1.1.5"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler design-project.handler/app}
  :main design-project.handler
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
