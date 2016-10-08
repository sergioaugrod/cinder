(ns cinder.transport.influxdb
  (:require [cinder.config :as config]
            [capacitor.core :as influxdb]))

(def ^:private client (influxdb/make-client config/influxdb))

(defn ping
  []
  (influxdb/ping client))

(defn point
  [measurement fields tags]
  {:measurement measurement :fields fields :tags tags})

(defn write-point
  [point]
  (influxdb/write-point client point))

(defn write-points
  [points]
  (influxdb/write-points client points))

(defn db-query
  [query]
  (influxdb/db-query client query true))
