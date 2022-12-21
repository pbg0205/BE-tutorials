package com.example.springfeignclient.mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ref : https://syaku.tistory.com/387 (WireMock custom annotation)
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "request.server.url=http://localhost:${wiremock.server.port}"
})
@ExtendWith(ApiServerMockExtensions.class)
public @interface WireMockClientTest {
}
