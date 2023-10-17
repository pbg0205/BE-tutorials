package com.example.springcore.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SampleAspect {

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("joinPoint.getTarget() : {}", joinPoint.getTarget());
		log.debug("joinPoint.getArgs() : {}", joinPoint.getArgs());
		log.debug("joinPoint.getSignature() : {}", joinPoint.getSignature());

		long start = System.currentTimeMillis();

		Object proceed = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - start;

		System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		return proceed;
	}
}
