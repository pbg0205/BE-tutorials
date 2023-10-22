package com.cooper.springaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TransactionAspect {

	@Pointcut("@annotation(com.cooper.springaop.annotation.CooperTransactional)")
	public void cooperTransaction(){}

	@Around("cooperTransaction()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("{} transaction start", joinPoint.getSignature());

		Object result = joinPoint.proceed();
		log.info("transaction result: {}", result);

		log.info("{} transaction end", joinPoint.getSignature());

		return result;
	}
}
