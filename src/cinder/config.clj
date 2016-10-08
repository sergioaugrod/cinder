(ns cinder.config)

(defn- env
  [attribute]
  (System/getenv attribute))

(def mqtt {:host (or (env "MQTT_RESOURCE") "tcp://localhost:1883")
           :username (or (env "MQTT_USERNAME") "")
           :password (or (env "MQTT_PASSWORD") "")
           :clean-session true})

(def influxdb {:host (or (env "INFLUX_HOST") "localhost")
               :scheme "http"
               :port (or (env "INFLUX_PORT") 8086)
               :username (or (env "INFLUX_USERNAME") "")
               :password (or (env "INFLUX_PASSWORD") "")
               :db (or (env "INFLUX_DB") "fulgore")
               :version (or (env "INFLUX_VERSION") "0.13")})

(def telegram {:token (env "TELEGRAM_TOKEN")
               :chat-ids (into [] (clojure.string/split (env "TELEGRAM_CHAT_IDS") #","))})
