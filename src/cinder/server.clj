(ns cinder.server
  (:gen-class)
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [clojure.core.async :as async]
            [cinder.event.telegram :as telegram]
            [cinder.service :as service]))

(defonce runnable-service (server/create-server service/service))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> service/service
      (merge {:env :dev
              ::server/join? false
              ::server/routes #(route/expand-routes (deref #'service/routes))
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}})
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nListening sensors...")
  (service/listener-sensors)
  (println "\nConnecting to telegram...")
  (telegram/start)
  (println "\nCreating your server...")
  (server/start runnable-service))
