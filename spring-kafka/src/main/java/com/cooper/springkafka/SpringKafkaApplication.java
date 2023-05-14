package com.cooper.springkafka;

import com.cooper.springkafka.messages.MessageDto;
import com.cooper.springkafka.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringKafkaApplication.class, args);

        KafkaProducer producer = context.getBean(KafkaProducer.class);
        producer.send("custom.topic", new MessageDto("cooper message", "ver.1.0"));
    }

}
