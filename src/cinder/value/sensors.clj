(ns cinder.value.sensors
  (:require [cinder.transport.influxdb :as influxdb]))

(defn- last-value
  [action]
  (-> (influxdb/db-query (str "SELECT last(value) FROM " action))
      ; TODO: Refactor this.
      :results first :series first :values first last))

(defn temperature
  []
  (last-value "temperature"))

(defn humidity
  []
  (last-value "humidity"))

(defn luminosity
  []
  (last-value "luminosity"))

(defn presence
  []
  (last-value "presence"))
