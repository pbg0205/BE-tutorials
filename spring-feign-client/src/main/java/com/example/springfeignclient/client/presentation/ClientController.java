package com.example.springfeignclient.client.presentation;

import com.example.springfeignclient.client.dto.OpenApiResponseDto;
import com.example.springfeignclient.client.feign_client.JsonPlaceHolderClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final JsonPlaceHolderClient jsonPlaceHolderClient;

    public ClientController(JsonPlaceHolderClient jsonPlaceHolderClient) {
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
    }

    @GetMapping("/v1")
    public ResponseEntity<OpenApiResponseDto> requestToServer(
        @RequestParam String serviceKey,
        @RequestParam(required = false) String type) {

        ResponseEntity<OpenApiResponseDto> response = jsonPlaceHolderClient.getRequest(serviceKey, type);
        log.info("openApiResponseDto : {}", response);

        return ResponseEntity.ok(response.getBody());
    }

}
