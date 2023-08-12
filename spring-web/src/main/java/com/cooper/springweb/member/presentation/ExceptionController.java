package com.cooper.springweb.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExceptionController {

    @GetMapping("/api/exception")
    public ResponseEntity<String> exception() {
        return ResponseEntity.ok("exception!!!");
    }
}
