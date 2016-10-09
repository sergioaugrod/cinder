(ns cinder.event.alert
  (:require [cinder.event.telegram :as telegram]
            [cinder.config :as config]
            [io.pedestal.log :as log]))

(def telegram-chat-id (first (config/telegram :chat-ids)))

(defn- alert-presence
  [message]
  (log/info :msg (str "[ALERT PRESENCE]: " message))
  (telegram/send-message telegram-chat-id message))

(defn call
  [topic value]
  (if (and (= topic "presence") (= value "1"))
    (alert-presence "Alguém passou pelo sensor de presença!")))
