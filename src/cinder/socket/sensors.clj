(ns cinder.socket.sensors
  (:import (java.nio ByteBuffer))
  (:require [clojure.core.async :as async]
            [cinder.event.sensors :as sensors]
            [clojure.data.json :as json]))

(def ^:private connections (atom {}))

(defn new-connection
  [session channel]
  (swap! connections assoc session channel))

(defn send-data
  [message]
  (doseq [[session channel] @connections]
    (if (.isOpen session)
      (async/put! channel (json/write-str message))
      (swap! connections dissoc session channel))))
