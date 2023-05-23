package com.cooper.springkafkabasic.consumer;

import com.cooper.springkafkabasic.messages.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "custom.topic", groupId = "sample.group.1", containerFactory = "messageListenerContainerFactory")
    public void listenGroupFoo(MessageDto message) {
        System.out.println("Received Message in group 'sample.group.1': " + message);
    }

}
