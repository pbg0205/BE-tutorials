package com.cooper.springaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class OrderRepositoryAspect {

	@Pointcut("execution(* com.cooper.springaop.api.*Repository*.*(..))")
	public void allOrder() {}

	@Around("allOrder()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("----- start: before invocation order repository");

		log.info("{} : {}", joinPoint.getSignature(), joinPoint.getArgs());

		log.info("----- end: before invocation order repository save method");
		Object result = joinPoint.proceed(); // target

		log.info("----- start: after invocation order repository save method");
		log.info("result : {}", result.toString());

		log.info("----- end: order repository aspect end");

		return result;
	}
}
