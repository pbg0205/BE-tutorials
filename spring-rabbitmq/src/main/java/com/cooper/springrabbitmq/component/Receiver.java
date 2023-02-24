package com.cooper.springrabbitmq.component;

import com.cooper.springrabbitmq.dto.AmqpBodyDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static com.cooper.springrabbitmq.constants.Constants.QUEUE_NAME;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(final AmqpBodyDto message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
