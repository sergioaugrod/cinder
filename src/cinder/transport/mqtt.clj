(ns cinder.transport.mqtt
  (:require [cinder.config :as config]
            [clojurewerkz.machine-head.client :as mqtt]))

(def ^:private host (config/mqtt :host))
(def ^:private generated-id (mqtt/generate-id))
(def ^:private opts {:username (config/mqtt :username)
                     :password (config/mqtt :password)
                     :clean-session (config/mqtt :clean-session)})

(defn connect
  []
  (def ^:private conn (mqtt/connect host generated-id opts)))

(defn publish
  [topic value]
  (mqtt/publish conn topic (str value)))

(defn subscribe
  [topics qos handler]
  (mqtt/subscribe conn {topics qos} handler))
