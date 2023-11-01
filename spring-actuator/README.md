# 프로덕션 준비 기능

- 프로덕션 준비 기능 : 운영 환경에서 서비스할 때 필요한 비기능적 요소들
- 예시 : 지표(metric), 추적(trace), 감사(auditing)
- actuator : 시스템을 움직이거나 제어하는 데 쓰이는 기계 장치

<br>

# 1. actuator 시작

## [1] 동작 확인

```bash
http://localhost:8080/actuator/health
```
```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
    },
    "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:8080/actuator/health/{*path}",
      "templated": true
    }
  }
}
```

<br>

## [2] application health status 확인

- 현재 서버가 잘 동작하고 있는지 애플리케이션의 헬스 상태를 확인

```bash
http://localhost:8080/actuator/health
```

```json
{
  "status":"UP"
}
```

<br>

## [3] actuator 기능 웹에 노출

### (1) 엔드포인트 기능 웹 노출 설정 (application.yml)

- 엔드포인트 : 액츄에이터가 제공하는 기능
- 예시
   - health - 헬스 정보
   - beans - 스프링 컨테이너 등록 빈

```yaml
management:
    endpoints:
      web:
        exposure:
          include: "*"

```

<br>

### (2) 요청 확인

```http request
http://localhost:8080/actuator
```
```json
      "info":{
         "href":"http://localhost:8080/actuator/info",
         "templated":false
      },
      "conditions":{
         "href":"http://localhost:8080/actuator/conditions",
         "templated":false
      },
      "configprops":{
         "href":"http://localhost:8080/actuator/configprops",
         "templated":false
      },
      "configprops-prefix":{
         "href":"http://localhost:8080/actuator/configprops/{prefix}",
         "templated":true
      },
      "env-toMatch":{
         "href":"http://localhost:8080/actuator/env/{toMatch}",
         "templated":true
      },
      "env":{
         "href":"http://localhost:8080/actuator/env",
         "templated":false
      },
      "loggers-name":{
         "href":"http://localhost:8080/actuator/loggers/{name}",
         "templated":true
      },
      "loggers":{
         "href":"http://localhost:8080/actuator/loggers",
         "templated":false
      },
      "heapdump":{
         "href":"http://localhost:8080/actuator/heapdump",
         "templated":false
      },
      "threaddump":{
         "href":"http://localhost:8080/actuator/threaddump",
         "templated":false
      },
      "metrics-requiredMetricName":{
         "href":"http://localhost:8080/actuator/metrics/{requiredMetricName}",
         "templated":true
      },
      "metrics":{
         "href":"http://localhost:8080/actuator/metrics",
         "templated":false
      },
      "scheduledtasks":{
         "href":"http://localhost:8080/actuator/scheduledtasks",
         "templated":false
      },
      "mappings":{
         "href":"http://localhost:8080/actuator/mappings",
         "templated":false
      }
   }
}
```

### (3) 헬스 정보 확인 

1. 애플리케이션 헬스 정보

```http request
http:://localhost:8080/actuator/health
```

```json
{
  "status":"UP"
}

```

2. 스프링 컨테이너에 등록된 빈

```http request
http:://localhost:8080/actuator/beans
```

```json
 {
  "contexts":{
    "application":{
      "beans":{
        "spring.jpa-org.springframework.boot.autoconfigure.orm.jpa.JpaProperties":{
          "aliases":[

          ],
          "scope":"singleton",
          "type":"org.springframework.boot.autoconfigure.orm.jpa.JpaProperties",
          "resource":null,
          "dependencies":[

          ]
        },
        "endpointCachingOperationInvokerAdvisor":{
          "aliases":[

          ],
          "scope":"singleton",
          "type":"org.springframework.boot.actuate.endpoint.invoker.cache.CachingOperationInvokerAdvisor",
          "resource":"class path resource [org/springframework/boot/actuate/autoconfigure/endpoint/EndpointAutoConfiguration.class]",
          "dependencies":[
            "org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration",
            "environment"
          ]
        },
        "defaultServletHandlerMapping":{
          "aliases":[

          ],
          "scope":"singleton",
          "type":"org.springframework.web.servlet.HandlerMapping",
          "resource":"class path resource [org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration$EnableWebMvcConfiguration.class]",
          "dependencies":[
            "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration$EnableWebMvcConfiguration"
          ]
        },
        "metricsRestTemplateCustomizer":{
          "aliases":[

          ],
          "scope":"singleton",
          "type":"org.springframework.boot.actuate.metrics.web.client.MetricsRestTemplateCustomizer",
          "resource":"class path resource [org/springframework/boot/actuate/autoconfigure/metrics/web/client/RestTemplateMetricsConfiguration.class]",
          "dependencies":[
            "org.springframework.boot.actuate.autoconfigure.metrics.web.client.RestTemplateMetricsConfiguration",
            "simpleMeterRegistry",
            "restTemplateExchangeTagsProvider",
            "management.metrics-org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties"
          ]
        },
        "applicationTaskExecutor":{
          "aliases":[
            "taskExecutor"
          ],
          "scope":"singleton",
          "type":"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor",
          "resource":"class path resource [org/springframework/boot/autoconfigure/task/TaskExecutionAutoConfiguration.class]",
          "dependencies":[
            "org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration",
            "taskExecutorBuilder"
          ]
        },
        "persistenceExceptionTranslationPostProcessor":{
          "aliases":[

          ],
          "scope":"singleton",
          "type":"org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor",
          "resource":"class path resource [org/springframework/boot/autoconfigure/dao/PersistenceExceptionTranslationAutoConfiguration.class]",
          "dependencies":[
            "environment"
          ]
        }
    // ...
}
```

## [4] 엔드포인트 설정

- 엔드 포인트 사용을 위해서는 `엔드포인트 활성화`, `엔드포인트 노출` 과정이 필요
  1. 엔드포인트 활성화 : 해당 기능 사용 여부(on, off) 를 선택 (default: 활성화!)
  2. 엔드포인트 노출 : 활성화 이후 HTTP, JMX 등의 위치에 노출 여부를 지정하는 것 
 

### (1) 엔드포인트 활성화: 모든 엔드포인트를 웹에 노출

- `management.endpoints.web.exposure.include: "*"` : 모든 엔드포인트를 웹에 노출하는 것
- `shutdown` : 실제 서버를 종료하는 엔드포인트(기본으로 비활성화되므로 노출되지 않음)

```yaml
management:
    endpoints:
      web:
        exposure:
          include: "*"
```

### (2) 엔드포인트 활성화: application.yml - shutdown 엔드포인트 활성화

- shutdown : 실제 서버를 종료하는 엔드포인트
- 특정 엔드포인트 활성법
   - `management.endpoint.{엔드포인트명}.enabled=true`
   - `POST` 요청 설정

```http request
POST /actuator/shutdown HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 27
```

```yaml
management:
    endpoint:
      shutdown:
        enabled: true # shutdown 엔드포인트 활성화
    endpoints:
      web:
        exposure:
          include: "*"
```

3. 결과
```json
{
  "message": "Shutting down, bye..."
}
```

<br>

### (3) 엔드포인트 활성화: application.yml - exposure include, exclude 설정

1. jmx 에 health, info 노출
    ```yaml
    management:
        endpoints:
          jmx:
            exposure:
              include: "health,info"
    ```

2. web 에 모든 엔드포인트 노출하지만 env, beans 제외
    ```yaml
    management:
        endpoints:
          web:
            exposure:
              include: "*"
              exclude: "env,beans"
    ```

### (4) 엔드 포인트 목록들

1. `beans` : 스프링 컨테이너에 등록된 스프링 빈을 보여준다.
2. `conditions` : condition 을 통해서 빈을 등록할 때 평가 조건과 일치하거나 일치하지 않는 이유를 표시한다.
3. `configprops` : @ConfigurationProperties 를 보여준다.
4. `env` : Environment 정보를 보여준다.
5. `health` : 애플리케이션 헬스 정보를 보여준다.
6. `httpexchanges` : HTTP 호출 응답 정보를 보여준다. **HttpExchangeRepository** 를 구현한 빈을 별도로 등록해야 한다.
7. `info` : 애플리케이션 정보를 보여준다.
8. `loggers` : 애플리케이션 로거 설정을 보여주고 변경도 할 수 있다.
9. `metrics` : 애플리케이션의 메트릭 정보를 보여준다.
10. `mappings` : @RequestMapping 정보를 보여준다.
11. `threaddump` : 쓰레드 덤프를 실행해서 보여준다.
12. `shutdown` : 애플리케이션을 종료한다. 이 기능은 기본으로 비활성화 되어 있다.

<br>

## 5. 헬스 정보

### (1) management.endpooint.health.show-details=always

- 헬스 정보를 자세하게 보기 위한 옵션 설정

```yaml
management:
    endpoint:
      health:
        show-details: always
```
```
GET /actuator/health HTTP/1.1
Host: localhost:8080
```
```json
{
    "status": "UP",
    "components": {
        "db": {
            "status": "UP",
            "details": {
                "database": "H2",
                "validationQuery": "isValid()"
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 1000240963584,
                "free": 816058421248,
                "threshold": 10485760,
                "exists": true
            }
        },
        "ping": {
            "status": "UP"
        }
    }
}
```

### (2) management.endpooint.health.show-components=always

```yaml
  management:
    endpoint:
      health:
        show-components: always
```

```json
{
    "status": "UP", // 하나라도 문제가 있으면 DOWN
    "components": {
        "db": {
            "status": "UP" 
        },
        "diskSpace": {
            "status": "UP"
        },
        "ping": {
            "status": "UP"
        }
    }
}
```

## 6. 애플리케이션 정보

### (1) info endpoint 

1. `info` : 애플리케이션 기본 정보를 노출

### (2) 기능 제공들

- 기능 제공 기능들 (env, java, os 는 기본 비활성화)
    1. `java` : 자바 런타임 정보
    2. `os` : OS 정보
    3. `env` : Environment 에서 info. 로 시작하는 정보
    4. `build` : 빌드 정보. (`META-INF/build-info.properties`) 파일이 필요
    5. `git` : git 정보, `git.properties` 파일이 필요

### (3) 설정 확인 : java, os

- `management.info.<id>.enabled: true` 로 설정
1. java, os 정보 확인
    ```yaml
      management:
        info:
          java:
            enabled: true
          os:
            enabled: true
    ```

2. 요청
    ```http request
    GET /actuator/info HTTP/1.1
    Host: localhost:8080
    ```

2. 응답
    ```json
    {
      "java": {
        "version": "11.0.19",
        "vendor": {
          "name": "Amazon.com Inc.",
          "version": "Corretto-11.0.19.7.1"
        },
        "runtime": {
          "name": "OpenJDK Runtime Environment",
          "version": "11.0.19+7-LTS"
        },
        "jvm": {
          "name": "OpenJDK 64-Bit Server VM",
          "vendor": "Amazon.com Inc.",
          "version": "11.0.19+7-LTS"
        }
      },
      "os": {
        "name": "Mac OS X",
        "version": "13.4.1",
        "arch": "x86_64"
      }
    }
    ```

<br>

### (4) 설정 확인 : env

1. env 정보 확인 설정 및 info 정보 설정
    ```yaml
    management:
      info:
        env:
          enabled: true
    
    info:
      app:
        name: hello-actuator
        company: cooper
    ```

2. 요청
    ```http request
    GET /actuator/info HTTP/1.1
    Host: localhost:8080
    ```

3. 응답
    ```json
    {
        "app": {
            "name": "hello-actuator",
            "company": "cooper"
        }
    }
    ```
    
<br>

### (5) 설정 확인 : build
    
1. build.gradle - 빌드 정보 추가
    ```groovy
    springBoot {
        buildInfo()
    }
    ```

2. 빌드 시 `resource/META-INF/build-info.properties` 생성

   <img width="248" alt="image" src="https://github.com/pbg0205/pbg0205/assets/48561660/9471fd5f-ec15-4752-b4fc-9dcad7030fbf">
    
   ```text
    build.artifact=spring-actuator
    build.group=com.cooper
    build.name=spring-actuator
    build.time=2023-11-01T06\:40\:15.037525Z
    build.version=0.0.1-SNAPSHOT
    ```

3. 요청
    ```http request
    GET /actuator/info HTTP/1.1
    Host: localhost:8080
    ```
   
4. 응답
    ```json
    {
      "build": {
        "artifact": "spring-actuator",
        "name": "spring-actuator",
        "time": "2023-11-01T06:43:41.431Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "com.cooper"
      }
    }
    ```
### (6) 설정 확인 : logger

1. log level 설정
    ```yaml
    logging:
      level:
        com.cooper.com.cooper.springactuator.controller: debug
    ```

2. 요청 : 전체 로거 레벨 파악
    ```http request
    GET /actuator/loggers HTTP/1.1
    Host: localhost:8080
    ```

3. 응답 : 전체 로거 레벨 파악
    ```json
    {
      "levels": [
        "OFF",
        "ERROR",
        "WARN",
        "INFO",
        "DEBUG",
        "TRACE"
      ],
      "loggers": {
        "ROOT": {
          "configuredLevel": "INFO",
          "effectiveLevel": "INFO"
        },
        "com.cooper.springactuator.controller": {
          "configuredLevel": "DEBUG",
          "effectiveLevel": "DEBUG"
        }
      }
    }
    ```
4. 요청 : 특정 로거 이름 기준 조회
    ```http request
    GET /actuator/loggers/com.cooper.springactuator.controller HTTP/1.1
    Host: localhost:8080
    ```
5. 응답 : 특정 로거 이름 기준 조회
    ```json
    {
        "configuredLevel": "DEBUG",
        "effectiveLevel": "DEBUG"
    }
    ```
6. 요청 : 실시간 로그 레벨 변경
    ```http request
    POST /actuator/loggers/com.cooper.springactuator.controller HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    Content-Length: 38
    
    {
          "configuredLevel": "TRACE"
      }
    ```
7. 응답 : 실시간 로그 레벨 변경
    ```text
    HTTP/1.1 204 No Content
    ```
8. 요청 로그 확인 
    ```text
    GET /log HTTP/1.1
    Host: localhost:8080
    ```
    ```text
    2023-11-01 16:03:15.280 TRACE 13323 --- [nio-8080-exec-2] c.c.s.controller.LogController           : trace log
    2023-11-01 16:03:15.282 DEBUG 13323 --- [nio-8080-exec-2] c.c.s.controller.LogController           : debug log
    2023-11-01 16:03:15.282  INFO 13323 --- [nio-8080-exec-2] c.c.s.controller.LogController           : info log
    2023-11-01 16:03:15.282  WARN 13323 --- [nio-8080-exec-2] c.c.s.controller.LogController           : warn log
    2023-11-01 16:03:15.282 ERROR 13323 --- [nio-8080-exec-2] c.c.s.controller.LogController           : error log
    ```
9. 요청 : 변경된 로그 레벨 확인
    ```http request
    GET /actuator/loggers/com.cooper.springactuator.controller HTTP/1.1
    Host: localhost:8080
    ```
10. 응답 : 변경된 로그 레벨 확인
    ```json
    // 응답 값
    {
        "configuredLevel": "TRACE",
        "effectiveLevel": "TRACE"
    }
    ```

## 7. HTTP 요청 응답 기록

- `httpexchanges` : HTTP 요청과 응답의 과거 기록 확인 엔트포인트
  - `boot ver >= 3.0` : `HttpExchangeRepository` 인터페이스의 구현체를 빈으로 등록하면 httpexchanges 엔드포인트 사용 가능
  - `boot ver == 2.xx` : `InMemoryHttpTraceRepository` 인터페이스의 구현체를 빈으로 등록하면 httpexchanges 엔드포인트 사용 가능

### (1) 요청 내역 확인

1. 요청 : 요청 내역 확인
   - `boot ver >= 3.0` :  `/actuator/httpexchange`
   - `boot ver == 2.xx` : `/actuator/httptrace`

    ```http request
    GET /actuator/httptrace HTTP/1.1
    Host: localhost:8080
    ```
2. 응답 : 요청 내역 확인
    ```json
    {
        "traces": [
            {
                "timestamp": "2023-11-01T07:10:26.858356Z",
                "principal": null,
                "session": null,
                "request": {
                    "method": "GET",
                    "uri": "http://localhost:8080/actuator",
                    "headers": {
                        "postman-token": [
                            "76ceeb8c-2879-4b12-b027-8214f46c9e94"
                        ],
                        "host": [
                            "localhost:8080"
                        ],
                        "connection": [
                            "keep-alive"
                        ],
                        "cache-control": [
                            "no-cache"
                        ],
                        "accept-encoding": [
                            "gzip, deflate, br"
                        ],
                        "user-agent": [
                            "PostmanRuntime/7.32.2"
                        ],
                        "accept": [
                            "*/*"
                        ]
                    },
                    "remoteAddress": null
                },
                "response": {
                    "status": 200,
                    "headers": {
                        "Transfer-Encoding": [
                            "chunked"
                        ],
                        "Keep-Alive": [
                            "timeout=60"
                        ],
                        "Connection": [
                            "keep-alive"
                        ],
                        "Date": [
                            "Wed, 01 Nov 2023 07:10:26 GMT"
                        ],
                        "Content-Type": [
                            "application/vnd.spring-boot.actuator.v3+json"
                        ]
                    }
                },
                "timeTaken": 11
            },
            {
                "timestamp": "2023-11-01T07:10:14.246796Z",
                "principal": null,
                "session": null,
                "request": {
                    "method": "GET",
                    "uri": "http://localhost:8080/actuator/htttptrace",
                    "headers": {
                        "postman-token": [
                            "dabf8562-81bf-465e-b683-d6f85c473bb5"
                        ],
                        "host": [
                            "localhost:8080"
                        ],
                        "connection": [
                            "keep-alive"
                        ],
                        "cache-control": [
                            "no-cache"
                        ],
                        "accept-encoding": [
                            "gzip, deflate, br"
                        ],
                        "user-agent": [
                            "PostmanRuntime/7.32.2"
                        ],
                        "accept": [
                            "*/*"
                        ]
                    },
                    "remoteAddress": null
                },
                "response": {
                    "status": 404,
                    "headers": {
                        "Vary": [
                            "Origin",
                            "Access-Control-Request-Method",
                            "Access-Control-Request-Headers"
                        ]
                    }
                },
                "timeTaken": 2
            }
        ]
    }
    ```

## [8] 액츄에이터와 보안

### (1) 보안 주의

- actuator 는 application 내부 정보가 많이 노출된다.
- actuator 는 외부에 접근 불가능하게 막고, 내부에서만 접근 가능한 내부망을 사용하는 것을 권장
- 보안을 유지할 수 있는 방법
  1. actuator port 변경
  2. actuator URL 경로 인증 설정
  3. endpoint 경로 변경

### (2) actuator 포트 변경

```yaml
management:
  server:
    port: 9282
```

```http request
http://localhost:9292/actuator
```

### (3) actuator URL 경로 인증 설정

- 포트 분리가 어렵고 외부 인터넷 망을 사용해 접근해야 한다면 /actuator 경로에 인증 사용자만 접근 가능하도록 추가 개발 필요

### (4) 엔드포인트 경로 변경

```yaml
management:
endpoints:
  web:
    base-path: "/manage"
```
1. `/actuator/{endpoint}` 대신 `/manage/{엔드포인트}` 로 변경
