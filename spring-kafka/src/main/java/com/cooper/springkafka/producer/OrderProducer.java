package com.cooper.springkafka.producer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProducer {

	@Qualifier("kafkaTemplate")
	private final KafkaTemplate<String, String> kafkaProducer;

	public void publish(final String order) {
		kafkaProducer.send("web.orders", order);
	}

}
