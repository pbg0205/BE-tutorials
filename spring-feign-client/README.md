## openfeign

## openfeign ??
- Feign 은 Netflix 에서 개발된 Http client binder 입니다.
- Feign 을 사용하면 웹 서비스 클라이언트를 보다 쉽게 작성할 수 있습니다.
- Feign 을 사용하기 위해서는 interface 를 작성하고 annotation 을 선언 하기만 하면됩니다.
  (마치 Spring Data JPA 에서 실제 쿼리를 작성하지 않고 Interface 만 지정하여 쿼리실행 구현체를 자동으로 만들어주는 것과 유사합니다.)

[출처 : [우아한 형제들 기술블로그] 우아한 feign 적용기](https://techblog.woowahan.com/2630/)

## 1. dependencies(gradle)
```groovy
dependencies {
    //...
    implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'
    //...
}

```

## 2. Configuration
```java
@Configuration
@EnableFeignClients(basePackages = "com.example.springfeignclient.client.*")
public class FeignClientConfig {
}
```
- **@EnableFeignClients** : Feign client 을 선언하는 인터페이스에 대한 componentscan 을 확성화하는 어노테이션
- 만약 @Configuration 에 명시할 경우 별도로 basePackages 를 할당시켜야 한다.

<br>

### Example

```java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @GetMapping
    public ResponseEntity<String> getRequest() {
        return ResponseEntity.ok("ok");
    }

}
```

```java
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "jplaceholder", url = "http://localhost:8080/")
public interface JsonPlaceHolderClient {

    @GetMapping("/api/sample")
    String getRequest();
    
}
```
- @FeignClient 어노테이션에 클라이언트 이름과 요청할 url 을 설정한다.
- spring web 에서 제공하는 어노테이션을 선언해서 요청할 API 를 선언할 수 있다.

<br>

## 3. Custom Beans Configuration

- feign 을 커스터마이징하는 하는 방법은 두가지 방법이 있다.
  1. @FeignClient confiruation 을 선언하는 방법
  2. properties 를 선언하는 방법
- properties 를 선언하느 방법의 경우 전역 범위를 설정하는 용도로 사용하고 @Configuration 어노테이션은 각각의 @FeignClient 를 커스터 마이징하는
  방식으로 사용한다. 다양한 요청에 대응하기 위해서는 @Configuration 방식이 더욱 다양한 요구사항에 대응할 수 있을 것 같다.

### 커스터 마이징할만한 설정들
1. 제한 시간 (timeout)
2. 시도 횟수 (retrial)
3. 로깅 (logging)

<br>

`@Configuration 을 이용한 설정`

```java
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static feign.Logger.Level;

@Configuration
@EnableFeignClients(basePackages = "com.example.springfeignclient.client.*")
public class FeignClientConfig {

    /**
     * @ref: https://www.baeldung.com/spring-cloud-openfeign
     *
     * NONE – no logging, which is the default
     * BASIC – log only the request method, URL and response status
     * HEADERS – log the basic information together with request and response headers
     * FULL – log the body, headers and metadata for both request and response
     *
     */
    @Bean
    Level feignLoggerLevel() {
        return Level.BASIC;
    }

    /**
     * - @ref : https://mangkyu.tistory.com/279
     * - Retryer 을 사용하면 period, timeout 을 설정할 수 있음. 
     */
    @Bean
    Retryer retryer() {
        // 0.1초의 간격으로 시작해 최대 3초의 간격으로 점점 증가하며, 최대5번 재시도한다.
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5);
    }

}
```

<br>

`프로퍼티를 이용한 설정`

- feign client 설정을 application properties 로 설정 또한 가능하다.
- 아래 예시는 timeout 설정과 연결 시간, 로깅 레벨을 선언한 내용이다.
- 이전에 보았던 client bean 설정 재정의 또한 properties 를 통해서 선언할 수 있다. 

```yaml
feign:
  client:
    config:
      default:
        connectTimeout: 5000 # unit : milli seconds
        readTimeout: 5000 # unit : milli seconds
        loggerLevel: basic
```

<br>

## 5. Interceptors

- RequestInterceptor 를 사용하면 요청을 가로채서 헤더 추가, 로깅, 인증 헤더 등을 추가할 수 있다.
- 방법에는 properties 와 @Configuration 선언 방법이 있다. 별도의 요청에 대해서 커스터 마이징하는 용도로 별도의 @Configuration 방법이 적합해 보인다.

```java
@Configuration
@EnableFeignClients(basePackages = "com.example.springfeignclient.client.*")
public class FeignClientConfig {

    /**
     * @ref: https://www.baeldung.com/spring-cloud-openfeign
     *
     * NONE – no logging, which is the default
     * BASIC – log only the request method, URL and response status
     * HEADERS – log the basic information together with request and response headers
     * FULL – log the body, headers and metadata for both request and response
     *
     */
    @Bean
    Level feignLoggerLevel() {
        return Level.BASIC;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("customHeader", "header1");
    }

}
```

## 5. Feign Integration Test

- 일반적으로 외부 API 요청하는 형태이므로 일반적으로는 stubbing 을 통해 테스트를 작성하는 경우가 많다.
  (stubbing : mock 객체의 행위를 조작하는 행위)
- 대표적인 예시 중 하나가 WireMock 이다.

### (1) dependency (build.gradle)
```groovy
dependencies {
	testImplementation 'com.github.tomakehurst:wiremock:2.27.2'
}

```

### 2. Stubbing By WireMock

```java
package com.example.springfeignclient.mock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public abstract class ApiServerMocks {

    private static final String REQUEST_URL = "/api/server/v1";

    private static StubMapping apiRequestStub;

    public static void ServerAllMocks() {
        setupSearchAddressStub();
    }

    public static void removeAllMocks() {
        WireMock.removeStub(apiRequestStub);
    }

    private static void setupSearchAddressStub() {
        apiRequestStub = stubFor(get(urlPathEqualTo(REQUEST_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("ServerController response by feign client")
                ));
    }

}

```
- @AutoConfigureWireMock : wiremock 의 서버 포트, 스텁 위치 등을 설정할 수 있는 어노테이션이다.
  - WireMock 서버용 포트를 직접 설정할 수 있지만 랜덤 포트 사용을 권장하고 있다. 이는 `port = 0` 을 선언하면 랜덤포트로 지정된다.
  - 랜덤 포트를 사용하면 실제 서버의 포트와 다를 수 있어 properties를 재설정하지만 
    @TestPropertySource 를 통해 테스트 환경에서 프로퍼티를 재정의할 수 있다.

(출처 : 샤인 공유 노션 + spring reference)
