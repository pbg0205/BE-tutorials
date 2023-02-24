package com.cooper.springrabbitmq.component;

import com.cooper.springrabbitmq.dto.AmqpBodyDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.cooper.springrabbitmq.constants.Constants.TOPIC_EXCHANGE_NAME;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");

        AmqpBodyDto amqpBodyDto = new AmqpBodyDto("cooper", "content");

        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "foo.bar.baz", amqpBodyDto);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
