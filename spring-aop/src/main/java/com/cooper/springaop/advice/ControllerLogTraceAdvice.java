package com.cooper.springaop.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerLogTraceAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			log.info("before invocation: {}", invocation.getThis().getClass());

			log.info("this: {}", invocation.getThis());
			Method method = invocation.getMethod();
			log.info("{} method:  {}", invocation.getThis().getClass().getName(), method);
			for (Object argument : invocation.getArguments()) {
				log.info("{} arguments:  {}", invocation.getThis().getClass().getName(), argument.toString());
			}

			Object result = invocation.proceed();
			log.info("{} after invocation", invocation.getThis().getClass().getName());

			return result;
		} catch (Exception e) {
			throw e;
		}
	}
}
