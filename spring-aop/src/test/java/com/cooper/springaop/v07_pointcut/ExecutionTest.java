package com.cooper.springaop.v07_pointcut;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springaop.pointcut.member.MemberServiceImpl;

@Slf4j
public class ExecutionTest {

	private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	private Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		//public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
		log.info("helloMethod={}", helloMethod);
	}
}
