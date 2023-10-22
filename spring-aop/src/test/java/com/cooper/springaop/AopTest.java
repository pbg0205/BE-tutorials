package com.cooper.springaop;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop.api.OrderRepository;
import com.cooper.springaop.api.OrderService;

@Slf4j
@SpringBootTest
class AopTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void aopInfo() {
		log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
		log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
	}

	@Test
	void exception() {
		assertThatThrownBy(() -> orderService.orderItem("ex", "item")).isInstanceOf(IllegalStateException.class);
	}
}
