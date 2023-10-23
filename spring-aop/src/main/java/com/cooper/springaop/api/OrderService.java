package com.cooper.springaop.api;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.cooper.springaop.annotation.CooperTransactional;

@Service
@RequiredArgsConstructor
@CooperTransactional
public class OrderService {

	private final OrderRepository orderRepository;

	@CooperTransactional
	public String orderItem(String key, String value) {
		return orderRepository.save(key, value);
	}

	public String noLog() {
		return "ok";
	}
}
