# zipkin-corellationId
Tracking microservice requests corellation ID with zipkin


Add org.springframework.cloud:spring-cloud-starter-sleuth dependency
Define spring.application.name
The name you give your application will show up as part of the tracing produced by Sleuth.

Sleuth adds to logs additional parameters
The call to /terminal
2017-01-25 16:54:50.023  INFO [microservice1,bfa58593580bae03,bfa58593580bae03,false] 7027 --- [nio-8080-exec-2] smartjava.in.ua.HelloController          : Called /terminal endpoint

first part is the application name
second value is the trace id
third value is the span id
Finally the last value indicates whether the span should be exported to Zipkin
 
Spring Cloud Sleuth adds the trace id and span id via headers in the request

The call to /transit
2017-01-25 17:03:07.705  INFO [microservice1,87f575fefba50843,0fcf845362a0188d,false] 7235 --- [nio-8080-exec-2] smartjava.in.ua.HelloController          : Called /terminal endpoint. Calling /endpoint.
2017-01-25 17:03:07.718  INFO [microservice1,87f575fefba50843,87f575fefba50843,false] 7235 --- [nio-8080-exec-1] smartjava.in.ua.HelloController          : Called /transit endpoint

the trace ids are the same but the span ids are different. The trace ids are what is going to allow you to trace a request as it travels from one service to the next. The span ids are different because we have two different “units of work” occurring, one for each request.