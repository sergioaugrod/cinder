(ns cinder.event.sensors
  (:require [cinder.transport.mqtt :as mqtt]
            [cinder.transport.influxdb :as influxdb]
            [cinder.event.alert :as alert]
            [clojure.core.async :as async]))

(def ^:private queues "sensors/#")
(def ^:private channel (async/chan))

(defn- log-message
  [message]
  (println
    (str "[SENSORS]:")
    (str "TOPIC: " (message :topic))
    (str "VALUE: " (message :value))))

(defn- format-topic
  [topic]
  (last (clojure.string/split topic #"/")))

(defn- format-point
  [message]
  (let [measurement (format-topic (message :topic)) fields {"value" (message :value)}]
    (influxdb/point measurement fields [])))

(defn- send-to-influxdb
  [message]
  (let [point (format-point message)]
    (influxdb/write-point point)))

(defn- push-to-channel
  [message]
  (async/go (async/>! channel message)))

(defn- subscribe
  [^String topic _ ^bytes value]
  (let [message {:topic topic :value (String. value "UTF-8")}]
    ;(log-message message)
    (alert/call (format-topic (message :topic)) (message :value))
    (push-to-channel message)
    (send-to-influxdb message)))

(defn- subscribe-queues
  []
  (mqtt/connect)
  (mqtt/subscribe queues 0 subscribe))

(defn execute
  []
  (subscribe-queues)
  channel)
