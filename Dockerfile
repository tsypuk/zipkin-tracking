FROM openjdk:8-jre-alpine

MAINTAINER Roman Tsypuk <tsypuk.rb@gmail.com>

ADD  build/libs/zipkin-0.0.1-SNAPSHOT.jar micro.jar
RUN sh -c 'touch /micro.jar'

WORKDIR /opt/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/micro.jar"]