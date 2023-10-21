package com.cooper.springaop.api;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public String order(String key, String value) {
		return orderRepository.save(key, value);
	}
}
