package com.cooper.springweb.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/whale")
public class WhaleController {

    @GetMapping
    public ResponseEntity<String> welcomeMessage(String whaleName) {
        return ObjectUtils.isEmpty(whaleName) ?
                ResponseEntity.ok("welcome someone") :
                ResponseEntity.ok(String.format("Hello %s", whaleName));
    }

    @GetMapping("/bye")
    public ResponseEntity<String> bye(String whaleName) {
        return ObjectUtils.isEmpty(whaleName) ?
                ResponseEntity.ok("bye someone") :
                ResponseEntity.ok(String.format("bye %s", whaleName));
    }

}
