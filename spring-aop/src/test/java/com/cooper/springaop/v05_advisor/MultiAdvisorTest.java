package com.cooper.springaop.v05_advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop.code.ServiceImpl;
import com.cooper.springaop.code.ServiceInterface;

public class MultiAdvisorTest {
	@Test
	@DisplayName("여러 프록시")
	void multiAdvisorTest1() {
		//client -> proxy02(advisor2) -> proxy01(advisor1) -> target
		//프록시1 생성
		ServiceInterface target = new ServiceImpl();

		ProxyFactory proxyFactory1 = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
		proxyFactory1.addAdvisor(advisor1);
		ServiceInterface proxy01 = (ServiceInterface)proxyFactory1.getProxy(); // proxy01 반환

		//프록시2 생성, target -> proxy01 입력
		ProxyFactory proxyFactory2 = new ProxyFactory(proxy01);
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
		proxyFactory2.addAdvisor(advisor2);
		ServiceInterface proxy02 = (ServiceInterface)proxyFactory2.getProxy(); // proxy02 반환

		//실행
		proxy02.save();
	}

	/**
	 * com.cooper.springaop.advisor.MultiAdvisorTest$Advice2 - advice2 호출
	 * com.cooper.springaop.advisor.MultiAdvisorTest$Advice1 - advice1 호출
	 * com.cooper.springaop.code.ServiceImpl - ServiceImpl - save 호출
	 */

	@Slf4j
	static class Advice1 implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice1 호출");
			return invocation.proceed();
		}
	}

	@Slf4j
	static class Advice2 implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice2 호출");
			return invocation.proceed();
		}
	}

	@Test
	@DisplayName("하나의 프록시, 여러 어드바이저") void multiAdvisorTest2() {
		//proxy -> advisor2 -> advisor1 -> target
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
		DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());

		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory1 = new ProxyFactory(target);

		proxyFactory1.addAdvisor(advisor2);
		proxyFactory1.addAdvisor(advisor1);

		ServiceInterface proxy = (ServiceInterface) proxyFactory1.getProxy();

		//실행
		proxy.save();
	}
	/**
	 * com.cooper.springaop.advisor.MultiAdvisorTest$Advice2 - advice2 호출
	 * com.cooper.springaop.advisor.MultiAdvisorTest$Advice1 - advice1 호출}
	 * com.cooper.springaop.code.ServiceImpl - ServiceImpl - save 호출
	 */
}
