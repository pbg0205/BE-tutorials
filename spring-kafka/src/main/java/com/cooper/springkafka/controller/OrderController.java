package com.cooper.springkafka.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import com.cooper.springkafka.domain.Order;
import com.cooper.springkafka.producer.OrderProducer;
import com.cooper.springkafka.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final OrderProducer orderProducer;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/api/order")
	public String createOrder(@RequestBody Order order) throws JsonProcessingException {
		final Order createOrder = orderService.createOrder(order);
		return objectMapper.writeValueAsString(createOrder);
	}
	
	@PostMapping("/api/order/publish")
	public String processOrder(@RequestBody String order) {
		orderProducer.publish(order);
		return "ok";
	}
	
}
