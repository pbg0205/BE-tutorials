package com.example.springcore.team.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TeamBeanImpl2 implements TeamBean {

    @Override
    public void logBeanName() {
        log.debug("{} {} 호출", TeamBeanImpl2.class.getName(), "logBeanName()");
    }

}
