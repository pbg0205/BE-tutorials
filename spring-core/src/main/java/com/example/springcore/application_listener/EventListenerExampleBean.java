package com.example.springcore.application_listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListenerExampleBean {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("{} {}",
                EventListenerExampleBean.class.getName(),
                "onApplicationEvent(ContextRefreshedEvent event) 선언"
        );
        log.debug("{}", event.getSource());
    }

}
