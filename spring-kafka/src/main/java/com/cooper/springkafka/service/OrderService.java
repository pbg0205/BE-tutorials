package com.cooper.springkafka.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.cooper.springkafka.domain.Order;
import com.cooper.springkafka.domain.Status;

@Service
public class OrderService {

	HashMap<UUID, Order> orders = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public Status findStatusById(UUID orderId) {
		logger.info("{}", orderId);
		return Status.ORDER_CONFIRMED;
	}

	public void processOrder(Order order) {
		logger.info("{}", order);
		order.setOrderProcessedTime(LocalDateTime.now());
		orders.put(order.getOrderId(), order);
	}

	public Order createOrder(final Order order) {
		UUID orderId = UUID.randomUUID();
		order.setOrderId(orderId);
		orders.put(orderId, order);
		return orders.get(orderId);
	}
}
