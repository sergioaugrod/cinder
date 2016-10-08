(ns cinder.service
  (:require [io.pedestal.http :as http]
            ;[io.pedestal.log :as log]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [clojure.core.async :as async]
            [cinder.event.sensors :as sensors-evt]
            [cinder.socket.sensors :as sensors-ws]
            [io.pedestal.http.jetty.websockets :as ws]))

; Routes

(defn home-page
  [request]
  (ring-resp/response "Home Instinct by @sergioaugrod"))

(def common-interceptors [(body-params/body-params) http/html-body])

(def routes #{["/" :get (conj common-interceptors `home-page)]})

; Web Sockets

(def ws-paths
  {"/sensors" {:on-connect (ws/start-ws-connection sensors-ws/new-connection)}})

; Listeners

(defn listener-sensors
  []
  (let [channel (sensors-evt/execute)]
    (async/thread
      (while true
        (sensors-ws/send-data (async/<!! channel))))))

; Service Configuration

(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/port 8080
              ::http/container-options {:context-configurator #(ws/add-ws-endpoints % ws-paths)
                                        :h2c? true
                                        :h2? false
                                        :ssl? false}})
