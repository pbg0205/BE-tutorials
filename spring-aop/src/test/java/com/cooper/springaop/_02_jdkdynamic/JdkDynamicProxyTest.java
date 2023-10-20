package com.cooper.springaop._02_jdkdynamic;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop._02_jdkdynamic.code.AInterface;
import com.cooper.springaop._02_jdkdynamic.code.AInterfaceImpl;
import com.cooper.springaop._02_jdkdynamic.code.BInterface;
import com.cooper.springaop._02_jdkdynamic.code.BInterfaceImpl;
import com.cooper.springaop._02_jdkdynamic.code.TimeInvocationHandler;

@Slf4j
public class JdkDynamicProxyTest {

	@Test
	void dynamicA() {
		AInterface target = new AInterfaceImpl();
		TimeInvocationHandler handler = new TimeInvocationHandler(target);

		AInterface proxy = (AInterface)Proxy.newProxyInstance(
			AInterface.class.getClassLoader(), // ClassLoader 정보
			new Class[] {AInterface.class}, // 인터페이스
			handler // 핸들러 로직 (부가로직)
		);

		proxy.call();

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());
	}
	/**
	 * [logs]
	 * TimeProxy 실행
	 * A 호출
	 * TimeProxy 종료 resultTime=0
	 * targetClass=class com.cooper.springaop.jdkdynamic.code.AInterfaceImpl
	 * proxyClass=class com.sun.proxy.$Proxy11
	 */

	@Test
	void dynamicB() {
		BInterface target = new BInterfaceImpl();
		TimeInvocationHandler handler = new TimeInvocationHandler(target);

		BInterface proxy = (BInterface)Proxy.newProxyInstance(
			BInterface.class.getClassLoader(), // ClassLoader 정보
			new Class[] {BInterface.class}, // 인터페이스
			handler // 핸들러 로직 (부가 로직)
		);

		proxy.call(); // 호출

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());
	}
	/**
	 * [logs]
	 * TimeProxy 실행
	 * B 호출
	 * TimeProxy 종료 resultTime=0
	 * targetClass=class com.cooper.springaop.jdkdynamic.code.BInterfaceImpl
	 * proxyClass=class com.sun.proxy.$Proxy11
	 */
}
