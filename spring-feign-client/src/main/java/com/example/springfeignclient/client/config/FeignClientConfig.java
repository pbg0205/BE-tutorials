package com.example.springfeignclient.client.config;

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

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("customHeader", "header1");
    }

}
