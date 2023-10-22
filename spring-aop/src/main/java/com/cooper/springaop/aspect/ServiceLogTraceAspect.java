package com.cooper.springaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ServiceLogTraceAspect {

	@Around("execution(* com.cooper.springaop.api.*Service*.*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		// target logic 완료 이전 로직
		log.info("===================================================== before advice");

		log.info("joinPoint.getThis() : {}", joinPoint.getThis()); // 적용 대상
		log.info("joinPoint.getTarget() : {}", joinPoint.getTarget()); // 적용 대상
		for (Object arg : joinPoint.getArgs()) {
			log.info("[joinPoint.getArgs()] type: {}, value: {}", arg.getClass(), arg); // arguments
		}
		log.info("joinPoint.getSignature() : {}", joinPoint.getSignature()); // [리턴타입] [패키지위치 + 클래스명].[메서드이름 + 파라미터 타입]
		log.info("joinPoint.getSignature().getName() : {}", joinPoint.getSignature().getName()); //
		log.info("joinPoint.getClass() : {}", joinPoint.getClass()); // class org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint

		log.info("=====================================================");

		Object result = joinPoint.proceed(); // target

		// target logic 완료 후 로직
		log.info("result : {}", result.toString()); // class org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint

		return result;
	}
}
