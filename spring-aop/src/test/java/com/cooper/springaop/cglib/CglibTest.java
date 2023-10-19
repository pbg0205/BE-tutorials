package com.cooper.springaop.cglib;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop.cglib.code.ConcreteService;
import com.cooper.springaop.cglib.code.TimeMethodInterceptor;

@Slf4j
public class CglibTest {

	@Test
	void cglib() {
		ConcreteService target = new ConcreteService();
		Enhancer enhancer = new Enhancer(); // CGLIB 은 Enhancer 를 사용해서 프록시를 생성한다.

		enhancer.setSuperclass(ConcreteService.class); // CGLIB 은 구체 클래스를 상속 받아서 프록시를 생성할 수 있다.
		enhancer.setCallback(new TimeMethodInterceptor(target)); // 프록시에 적용할 로직을 할당한다.
		ConcreteService proxy = (ConcreteService)enhancer.create(); // 프록시 생성

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.call(); // proxy 의 메서드 호출
	}

	/**
	 * targetClass=class com.cooper.springaop.cglib.code.ConcreteService
	 * proxyClass=class com.cooper.springaop.cglib.code.ConcreteService$$EnhancerByCGLIB$$9f96e347
	 * TimeProxy 실행
	 * ConcreteService 호출
	 * TimeProxy 종료 resultTime=12
	 */
}

