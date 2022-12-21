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
