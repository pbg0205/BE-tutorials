package com.cooper.springkafkabasic;

import com.cooper.springkafkabasic.messages.MessageDto;
import com.cooper.springkafkabasic.producer.KafkaProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringKafkaBasicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringKafkaBasicApplication.class, args);

        KafkaProducer producer = context.getBean(KafkaProducer.class);
        producer.send("custom.topic", new MessageDto("cooper message", "ver.1.0"));
        producer.send("custom.topic.error", new MessageDto("cooper message", "ver.1.0"));
    }

}
