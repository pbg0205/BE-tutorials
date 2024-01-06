package com.example.springfeignclient.client.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springfeignclient.client.config.FeignClientConfig;
import com.example.springfeignclient.client.dto.OpenApiResponseDto;

@FeignClient(
        value = "jsonPlaceHolderClient",
        url = "${request.server.url}",
        configuration = FeignClientConfig.class
)
public interface JsonPlaceHolderClient {

    @GetMapping("/getPwnmTabooInfoList03")
    ResponseEntity<OpenApiResponseDto> getRequest(@RequestParam String serviceKey, @RequestParam String type);

}
