package com.example.springfeignclient.client.feign_client;

import com.example.springfeignclient.client.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "jsonPlaceHolderClient",
        url = "${request.server.url}",
        configuration = FeignClientConfig.class
)
public interface JsonPlaceHolderClient {

    @GetMapping("/api/server/v1")
    ResponseEntity<String> getRequest();

}
