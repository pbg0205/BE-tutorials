package com.cooper.springaop._04_proxyfactory_advice.proxyfactory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop.code.ConcreteService;
import com.cooper.springaop._04_proxyfactory_advice.advice.TimeAdvice;
import com.cooper.springaop.code.ServiceImpl;
import com.cooper.springaop.code.ServiceInterface;

@Slf4j
public class ProxyFactoryTest {
	@Test
	@DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
	void interfaceProxy() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());

		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.save();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
		assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
	}
	/**
	 * targetClass=class com.cooper.springaop.cglib.code.ServiceImpl
	 * proxyClass=class com.sun.proxy.$Proxy12
	 * TimeProxy 실행
	 * ServiceImpl - ServiceImpl - save 호출
	 * TimeProxy 종료 resultTime=0ms
	 */

	@Test
	@DisplayName("구체 클래스만 있으면 CGLIB 사용")
	void concreteProxy() {
		ConcreteService target = new ConcreteService();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());

		ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.call();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
		assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
	}
	/**
	 * proxyClass=class com.cooper.springaop.cglib.code.ConcreteService$$EnhancerBySpringCGLIB$$4ecd05b1
	 * TimeProxy 실행
	 * ConcreteService - ConcreteService 호출
	 * TimeProxy 종료 resultTime=16ms
	 */
}

