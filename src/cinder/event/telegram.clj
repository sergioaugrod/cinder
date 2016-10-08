(ns cinder.event.telegram
  (:require [morse.handlers :refer :all]
            [morse.polling :as p]
            [morse.api :as  api]
            [cinder.value.sensors :as value]
            [cinder.config :as config]))

(def ^:private token (config/telegram :token))
(def ^:private chat-ids (config/telegram :chat-ids))

(defn- authorized?
  [chat-id]
  (.contains chat-ids (str chat-id)))

(defn- handle-action
  [action]
  (case action
    :temperature (value/temperature)
    :humidity (value/humidity)
    :luminosity (value/luminosity)
    :presence (value/presence)))

(defn send-message
  [chat-id message]
  (api/send-text token chat-id message))

(defn- handle-command
  [chat-id action]
  (if (authorized? chat-id)
    (let [result (handle-action action)]
      (send-message chat-id result))))

(defhandler bot-api
  (command "get_temperature"
           {{:keys [id]} :chat}
           (handle-command id :temperature))
  (command "get_humidity"
           {{:keys [id]} :chat}
           (handle-command id :humidity))
  (command "get_luminosity"
           {{:keys [id]} :chat}
           (handle-command id :luminosity))
  (command "get_presence"
           {{:keys [id]} :chat}
           (handle-command id :presence)))

(defn start
  []
  (def channel (p/start (config/telegram :token) bot-api)))

(defn stop
  []
  (p/stop channel))
