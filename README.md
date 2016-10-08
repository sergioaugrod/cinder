# Cinder

Receives data from a MQTT broker and serves to different sources.

## Features

* MQTT as a data source
* InfluxDB support
* Telegram commands
* Alerts to Telegram chats
* Broadcast websockets

## Requirements

* InfluxDB
* MQTT (Mosquitto)
* Telegram BOT API Key
* Clojure

## Installation

```console
$ git clone https://www.github.com/sergioaugrod/cinder
$ cd cinder
$ lein deps
```

Set environment variables:

```bash
export MQTT_RESOURCE=tcp://localhost:1883
export MQTT_USERNAME=admin
export MQTT_PASSWORD=test123

export INFLUX_HOST=localhost
export INFLUX_PORT=8086
export INFLUX_USERNAME=admin
export INFLUX_PASSWORD=test123
export INFLUX_DB=fulgore
export INFLUX_VERSION=0.13

export TELEGRAM_TOKEN=58daWjZwda774
export TELEGRAM_CHAT_IDS=XXXXX,YYYYY,ZZZZZ
```

# Development

```console
$ lein repl
```

```clojure
(def dev-serv (run-dev))
```

Connect your editor to the REPL.

## Usage

Console:

```console
$ lein run
```

JAR:

```console
$ lein uberjar
$ java -jar target/cinder-0.0.1-SNAPSHOT-standalone.jar
```

Docker:

```console
$ lein uberjar
$ docker build -t cinder .
$ docker run -p 8080:8080 cinder
```

## Contributing

1. Clone it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## Maintainers

* [sergioaugrod](https://github.com/sergioaugrod/)
