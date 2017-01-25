FROM openjdk:8-jre-alpine

MAINTAINER Roman Tsypuk <tsypuk.rb@gmail.com>

#################################
# Build and environment variables
#################################
ENV AIR_SLEEP 60
ENV TERM xterm
ENV TZ=Europe/Kiev
#ENV SPRING_PROFILES_ACTIVE docker

# Adding directly the jar-file
ADD  build/libs/zipkin-0.0.1-SNAPSHOT.jar zipkin.jar
RUN sh -c 'touch /airtraffic.jar'

#######################
# FileBeat Installation
#######################
# Fix the issue with wget and certificates:
# https://github.com/Yelp/dumb-init/issues/73
RUN apk --no-cache add --update ca-certificates bash wget ca-certificates openssl wget curl python mc vim tzdata \
    && update-ca-certificates

RUN cp /usr/share/zoneinfo/$TZ /etc/localtime
RUN echo $TZ > /etc/timezone
RUN apk del tzdata

WORKDIR /opt/

CMD echo "Starting AirTraffic application ${AIR_SLEEP}s..." \
    && sleep ${AIR_SLEEP} \
    && java -Djava.security.egd=file:/dev/./urandom -jar /zipkin.jar