package com.cooper.springkafka.producer;

import com.cooper.springkafka.dto.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GreetingProducer {

    @Qualifier("greetingKafkaTemplate")
    private final KafkaTemplate<String, Greeting> kafkaProducer;

    public void sendMessage(Greeting greeting) {
        kafkaProducer.send("greeting", greeting);
    }

}
