package com.cooper.springaop.code;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

	private final Object target;

	public TimeMethodInterceptor(Object target) {
		this.target = target;
	}

	@Override
	public Object intercept(
		Object obj, // cglib 적용된 객체
		Method method, // 호출된 메서드
		Object[] args, // 메서드를 호출하면서 전달된 인수
		MethodProxy proxy // 메서드 호출에 사용
	) throws Throwable {

		log.info("TimeProxy 실행");
		long startTime = System.currentTimeMillis();

		Object result = proxy.invoke(target, args);

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("TimeProxy 종료 resultTime={}", resultTime);

		return result;
	}
}
