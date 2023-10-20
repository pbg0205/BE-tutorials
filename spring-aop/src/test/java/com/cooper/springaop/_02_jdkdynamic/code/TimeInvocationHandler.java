package com.cooper.springaop._02_jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * JDK 동적 프록시에 적용할 로직은 InvocationHandler interface 를 구현해서 작성하면 된다.
 */
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

	private final Object target;

	public TimeInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(
		Object proxy, // proxy 자신
		Method method, // 호출한 메서드
		Object[] args // 메서드를 호출할 때 전달한 이수
	) throws Throwable {
		log.info("TimeProxy 실행");
		long startTime = System.currentTimeMillis();

		/**
		 * - reflection 을 사용해 target 인스턴스의 메서드를 실행한다.
		 * - args : 메서드 호출시 넘겨울 인수
		 */
		Object result = method.invoke(target, args);

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeProxy 종료 resultTime={}", resultTime);

		return result;
	}
}
