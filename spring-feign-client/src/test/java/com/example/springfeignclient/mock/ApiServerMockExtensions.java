package com.example.springfeignclient.mock;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @ref : https://www.baeldung.com/spring-cloud-feign-integration-tests
 * @ref : https://www.baeldung.com/junit-5-extensions
 */
public class ApiServerMockExtensions implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext context) {
        ApiServerMocks.removeAllMocks();
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        ApiServerMocks.ServerAllMocks();
    }

}
