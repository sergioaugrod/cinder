(defproject cinder "0.0.1-SNAPSHOT"
  :description "Cinder"
  :url "https://github.com/sergioaugrod/cinder"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.pedestal/pedestal.service "0.5.1"]
                 [io.pedestal/pedestal.jetty "0.5.1"]
                 [ch.qos.logback/logback-classic "1.1.7" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.21"]
                 [org.slf4j/jcl-over-slf4j "1.7.21"]
                 [org.slf4j/log4j-over-slf4j "1.7.21"]
                 [org.clojure/data.json "0.2.6"]
                 [clojurewerkz/machine_head "1.0.0-beta9"]
                 [capacitor "0.6.0"]
                 [morse "0.2.0"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "cinder.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.1"]]}
             :uberjar {:aot [cinder.server]}}
  :main ^{:skip-aot true} cinder.server)
