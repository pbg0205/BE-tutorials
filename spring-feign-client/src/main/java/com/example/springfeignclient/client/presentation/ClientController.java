package com.example.springfeignclient.client.presentation;

import com.example.springfeignclient.client.feign_client.JsonPlaceHolderClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final JsonPlaceHolderClient jsonPlaceHolderClient;

    public ClientController(JsonPlaceHolderClient jsonPlaceHolderClient) {
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
    }

    @GetMapping("/v1")
    public ResponseEntity<String> requestToServer() {
        ResponseEntity<String> response = jsonPlaceHolderClient.getRequest();
        return ResponseEntity.ok(response.getBody());
    }

}
