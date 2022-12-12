package com.example.springcore.team.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@Component
public class TeamBeanImpl1 implements TeamBean {

    @Override
    public void logBeanName() {
        log.debug("{} {} 호출", TeamBeanImpl1.class.getName(), "logBeanName()");
    }

}
