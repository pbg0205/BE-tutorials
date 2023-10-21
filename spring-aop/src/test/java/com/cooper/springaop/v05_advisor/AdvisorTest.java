package com.cooper.springaop.v05_advisor;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop.code.ServiceImpl;
import com.cooper.springaop.code.ServiceInterface;
import com.cooper.springaop.v04_proxyfactory_advice.advice.TimeAdvice;

@Slf4j
public class AdvisorTest {
	@Test
	@DisplayName("DefaultPointAdvisor 를 사용해본다")
	void advisorTest01() {
		ServiceInterface target = new ServiceImpl();

		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor // Advisor = advice + pointcut
			= new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); // Advisor 의 일반적인 구현체
		proxyFactory.addAdvisor(advisor); // proxyfactory 에 advisor 지정

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	/**
	 * #save() 호출
	 * TimeProxy 실행
	 * ServiceImpl - save 호출
	 * TimeProxy 종료 resultTime=0ms
	 *
	 * #find() 호출
	 * TimeProxy 실행
	 * ServiceImpl - find 호출
	 * TimeProxy 종료 resultTime=0ms
	 */

	@Test
	@DisplayName("custom point 을 선언한다")
	void advisorTest02() {
		ServiceInterface target = new ServiceImpl();

		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor // Advisor = advice + pointcut
			= new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice()); // Advisor 의 일반적인 구현체
		proxyFactory.addAdvisor(advisor); // proxyfactory 에 advisor 지정

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}
	/**
	 * # save - pointcut true
	 * 포인트컷 호출 method=save targetClass=class com.cooper.springaop.code.ServiceImpl
	 * 포인트컷 결과 result=true
	 * TimeProxy 실행
	 * save 호출
	 * TimeProxy 종료 resultTime=0ms
	 *
	 * # find - pointcut false
	 * 포인트컷 호출 method=find targetClass=class com.cooper.springaop.code.ServiceImpl
	 * 포인트컷 결과 result=false
	 * find 호출
	 */

	static class MyPointcut implements Pointcut {

		@Override
		public ClassFilter getClassFilter() { // 클래스 비교 필터
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() { // 메서드 비교 필터
			return new MyMethodMatcher();
		}
	}

	static class MyMethodMatcher implements MethodMatcher {

		private String matchName = "save";

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			/**
			 * - method , targetClass 정보가 넘어온다.
			 * - 이 정보로 어드바이스를 적용할지 적용하지 않을지 판단
			 */

			boolean result = method.getName().equals(matchName);
			log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
			log.info("포인트컷 결과 result={}", result);
			return result;
		}

		@Override
		public boolean isRuntime() {
			/**
			 * 1. false
			 * 	- 캐싱한다.
			 * 	- 클래스의 정적 정보만 사용하기 때문에 스프링 내부에서 캐싱을 통해 성능 향상 가능
			 *
			 * 2. true
			 * 	- 캐싱하지 않음.
			 * 	- 매개변수가 동적으로 변경된다고 가정
			 */
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			throw new UnsupportedOperationException();
		}
	}

	@Test
	@DisplayName("스프링이 제공하는 NameMatchMethodPointcut 을 선언한다")
	void advisorTest03() {
		ServiceInterface target = new ServiceImpl();

		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sa**"); // sa 로 시작하는 메서드 호출

		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor // Advisor = advice + pointcut
			= new DefaultPointcutAdvisor(pointcut, new TimeAdvice()); // Advisor 의 일반적인 구현체
		proxyFactory.addAdvisor(advisor); // proxyfactory 에 advisor 지정

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

}
