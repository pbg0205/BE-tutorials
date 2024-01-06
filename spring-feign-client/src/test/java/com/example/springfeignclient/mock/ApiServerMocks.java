package com.example.springfeignclient.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.springfeignclient.client.dto.OpenApiResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

public abstract class ApiServerMocks {

	private static final String REQUEST_URL = "/getPwnmTabooInfoList03";

	private static StubMapping apiRequestStub;

	public static void ServerAllMocks() {
		setupSearchAddressStub();
	}

	public static void removeAllMocks() {
		WireMock.removeStub(apiRequestStub);
	}

	private static void setupSearchAddressStub() {
		apiRequestStub = stubFor(
			get(urlPathEqualTo(REQUEST_URL))
				.withName("https://apis.data.go.kr/1471000") // hostname
				.withQueryParam("serviceKey", equalTo("serviceKey")) // query_param
				.withQueryParam("type", equalTo("json")) // query_param
				.willReturn(aResponse()
					.withStatus(HttpStatus.OK.value())
					.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.withBody(convertToJson(
						Paths.get("src/test/resources/json_data/response_body.json").toFile(),
						OpenApiResponseDto.class)
					)
				)
		);
	}

	private static String convertToJson(File dataFile, Class<?> classType) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			Object data = objectMapper.readValue(dataFile, classType);
			return objectMapper.writeValueAsString(data);
		} catch (IOException ioException) {
			throw new RuntimeException(ioException.getMessage(), ioException);
		}

	}

}
