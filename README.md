# ZIPKIN-CORELLATIONID

## START ZIPKIN DOCKER
Start docker container with zipkin
#### docker run -d -p 9411:9411 openzipkin/zipkin

Tracking microservice requests corellation ID with zipkin

## UPDATE APPLICATION TO USE SLEUTH
Add org.springframework.cloud:springпеш-cloud-starter-sleuth dependency
Define spring.application.name=micro#
The name you give your application will show up as part of the tracing produced by Sleuth.

## SLEUTH ADDS TO LOGS ADDITIONAL PARAMETERS
The call to /terminal
```
2017-01-25 16:54:50.023  INFO [microservice1,bfa58593580bae03,bfa58593580bae03,false] 7027 --- [nio-8080-exec-2] smartjava.in.ua.HelloController          : Called /terminal endpoint
```
<ul>
<li>First part is the application name</li>
<li>Second value is the trace id</li>
<li>Third value is the span id</li>
<li>Finally the last value indicates whether the span should be exported to Zipkin (true/false)</li>
</ul>
Spring Cloud Sleuth adds the trace id and span id via headers in the request

The call to /transit
```
2017-01-25 17:03:07.705  INFO [microservice1,87f575fefba50843,0fcf845362a0188d,false] 7235 --- [nio-8080-exec-2] smartjava.in.ua.HelloController          : Called /terminal endpoint. Calling /endpoint.
2017-01-25 17:03:07.718  INFO [microservice1,87f575fefba50843,87f575fefba50843,false] 7235 --- [nio-8080-exec-1] smartjava.in.ua.HelloController          : Called /transit endpoint
```
The trace ids are the same but the span ids are different. 
The trace ids are what is going to allow you to trace a request as it travels from one service to the next. 
The span ids are different because we have two different “units of work” occurring, one for each request.

## TODO ITEMS

- [x] Created microservices for correlation ID tracing
- [ ] Add docker integration for zipkin and every microservice container.