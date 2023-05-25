package com.cooper.springkafkabasic.consumer;

import com.cooper.springkafkabasic.messages.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "custom.topic", groupId = "sample.group.1", containerFactory = "messageListenerContainerFactory")
    public void listenGroupFoo(MessageDto message) {
        System.out.println(String.format("[Received consumer] topic: %s, message: %s", "custom.topic", message));
    }

    @KafkaListener(
            topics = "custom.topic.error",
            groupId = "sample.group.1",
            containerFactory = "messageListenerContainerFactory"
    )
    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "5",
            timeout = "30000", // 재시도 total timeout 시간. (초과 시, 재시도 없이 발생했던 예외 반환 및 DLT 로 이동)
            autoCreateTopics = "false",
            include = RuntimeException.class,
            kafkaTemplate = "kafkaMessageTemplate")
    public void listenGroupFooError(MessageDto message) {
        System.out.println(String.format("[Received consumer] topic: %s, message: %s, thread name: %s",
                "custom.topic.error",
                message,
                Thread.currentThread().getName()
        ));

        throw new RuntimeException("error occur!!");
    }

}
