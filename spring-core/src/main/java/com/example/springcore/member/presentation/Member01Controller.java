package com.example.springcore.member.presentation;

import com.example.springcore.team.beans.TeamBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Member01Controller {

    private final TeamBean teamBeanImpl1;

    @GetMapping("/api/v1/members/1")
    public ResponseEntity<String> print() {
        teamBeanImpl1.logBeanName();
        return ResponseEntity.ok("ok");
    }

}
