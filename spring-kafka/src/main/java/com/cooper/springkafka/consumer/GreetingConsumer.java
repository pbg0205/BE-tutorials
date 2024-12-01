package com.cooper.springkafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springkafka.dto.Greeting;

@Component
@Slf4j
public class GreetingConsumer {

    @KafkaListener(
            topics = "${greeting.topic.name}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void greetingListener(Greeting greeting) {
        log.info("received message: {}", greeting);
    }

}
