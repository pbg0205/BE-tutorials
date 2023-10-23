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
public class CooperTransactionalAnnotationAspect {

	// 메서드가 주어진 어노테이션을 가지고 있는 조인 포인트를 매칭
	@Pointcut("@annotation(com.cooper.springaop.annotation.CooperTransactional)")
	public void cooperTransactionAnnotation(){}

	@Around("cooperTransactionAnnotation()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("{} transaction start", joinPoint.getSignature());

		Object result = joinPoint.proceed();
		log.info("transaction result: {}", result);

		log.info("{} transaction end", joinPoint.getSignature());

		return result;
	}
}
