# ZIPKIN-CORRELATIONID

Created 3 microservice docker containers that communicate with each other. Each microservice has 2 end points. First terminal and second /transit
that calls next microservice and does the chaining.
By using random number in condition (new Random().nextInt(5) > 0) is choosen to do next chain call or not.
![Zipkin1](https://github.com/tsypuk/zipkin-tracking/blob/master/config/img/zipkin1.png)

## TERMINOLOGY
####Span:
The basic unit of work. For example, sending an RPC is a new span, as is sending a response to an RPC. Span’s are identified by a unique 64-bit ID for the span and another 64-bit ID for the trace the span is a part of. Spans also have other data, such as descriptions, timestamped events, key-value annotations (tags), the ID of the span that caused them, and process ID’s (normally IP address).
Spans are started and stopped, and they keep track of their timing information. Once you create a span, you must stop it at some point in the future.
####Trace: 
A set of spans forming a tree-like structure. For example, if you are running a distributed big-data store, a trace might be formed by a put request.

## USE ANSIBLE TO BUILD AND RUN
Run ansible runbook to build docker image and start containers with microservices and container
with Zipkin. Plus ansible will do 3 http get call to all microservices to add correlation IDs to zipkin. 
#### ansible-playbook src/main/ansible/RunMicros.yml
![Zipkin2](https://github.com/tsypuk/zipkin-tracking/blob/master/config/img/zipkin2.png)
## PORTS SCHEMA
```
service     container port  localmachine port
micro1      8080            http://localhost:8081
micro2      8080            http://localhost:8082
micro3      8080            http://localhost:8083
zipkin      9411            http://localhost:9411
```
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
- [x] Add docker integration for zipkin and every microservice container.
- [x] Added ansible build for all microservices.