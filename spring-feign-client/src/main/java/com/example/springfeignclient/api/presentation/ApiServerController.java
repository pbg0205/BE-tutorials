package com.example.springfeignclient.api.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/server")
public class ApiServerController {

    private final Logger logger = LoggerFactory.getLogger(ApiServerController.class);

    @GetMapping("/v1")
    public ResponseEntity<String> getRequest(@RequestHeader String customHeader) {
        logger.debug("header name : {}", customHeader);
        return ResponseEntity.status(HttpStatus.OK)
                .header("customHeader", customHeader)
                .build();
    }

}
