package com.example.springcore.member.presentation;

import com.example.springcore.team.beans.TeamBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Member02Controller {

    //파라미터로 @Qualifier 을 명시하지 않으면 필드 이름을 빈 이름과 동일하게 입력해도 동작하지 않는다.
    private final TeamBean teamBean;

    public Member02Controller(@Qualifier("teamBeanImpl2") TeamBean teamBean) {
        this.teamBean = teamBean;
    }

    @GetMapping("/api/v1/members/2")
    public ResponseEntity<String> print() {
        teamBean.logBeanName();
        return ResponseEntity.ok("ok");
    }

}
