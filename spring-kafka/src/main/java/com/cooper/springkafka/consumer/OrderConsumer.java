package com.cooper.springkafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.listener.KafkaBackoffException;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cooper.springkafka.domain.Order;
import com.cooper.springkafka.domain.Status;
import com.cooper.springkafka.service.OrderService;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

	private final ObjectMapper objectMapper;
	private final OrderService orderService;

	@RetryableTopic(attempts = "1", include = KafkaBackoffException.class, dltStrategy = DltStrategy.NO_DLT,
		kafkaTemplate = "kafkaTemplate")
	@KafkaListener(
		topics = {"web.orders", "web.internal.orders"},
		groupId = "orders",
		containerFactory = "orderKafkaListenerContainerFactory"
	)
	public void handleOrders(String order) throws JsonProcessingException {
		log.info("received message: {}", order);

		Order orderDetails = objectMapper.readValue(order, Order.class);
		Status orderStatus = orderService.findStatusById(orderDetails.getOrderId());
		if (orderStatus.equals(Status.ORDER_CONFIRMED)) {
			orderService.processOrder(orderDetails);
		}
	}
}
