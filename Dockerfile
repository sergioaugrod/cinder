FROM java:8-alpine
MAINTAINER sergioaugrod <sergioaugrod@gmail.com>

ADD target/cinder-0.0.1-SNAPSHOT-standalone.jar /cinder/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/cinder/app.jar"]
