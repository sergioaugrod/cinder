(ns cinder.event.alert
  (:require [cinder.event.telegram :as telegram]
            [cinder.config :as config]))

(def telegram-chat-id (first (config/telegram :chat-ids)))

(defn- alert-presence
  []
  (telegram/send-message telegram-chat-id "Alguém passou pelo sensor de presença! :D"))

(defn call
  [topic value]
  (if (and (= topic "presence") (= value "1"))
    (alert-presence)))
