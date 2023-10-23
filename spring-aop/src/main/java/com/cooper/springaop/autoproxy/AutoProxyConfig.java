package com.cooper.springaop.autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cooper.springaop.advice.ControllerLogTraceAdvice;
import com.cooper.springaop.advice.WithInAnnotationLogTraceAdvice;
import com.cooper.springaop.advice.WithInLogTraceAdvice;

@Configuration
public class AutoProxyConfig {

	// @Bean
	public Advisor advisor01() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("request*", "order*", "save*");
		ControllerLogTraceAdvice advice = new ControllerLogTraceAdvice();
		//advisor = pointcut + advice
		return new DefaultPointcutAdvisor(pointcut, advice);
	}

	/**
	 * AnnotationAwareAspectJAutoProxyCreator 에 의해 매핑되서 프록시가 생성
	 */
	@Bean
	public Advisor advisor02() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* com.cooper.springaop.api.*Controller*.*(..)) && !execution(* com.cooper.springaop.api..noLog(..))");
		ControllerLogTraceAdvice advice = new ControllerLogTraceAdvice();
		//advisor = pointcut + advice
		return new DefaultPointcutAdvisor(pointcut, advice);
	}

	@Bean
	public Advisor advisor03() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("within(com.cooper.springaop.api..*)");
		WithInLogTraceAdvice advice = new WithInLogTraceAdvice();
		//advisor = pointcut + advice
		return new DefaultPointcutAdvisor(pointcut, advice);
	}
	@Bean
	public Advisor advisor04() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		//pointcut.setExpression("within(@com.cooper.springaop.annotation.CooperTransaction)");
		// 주어진 어노테이션이 있는 타입 내 모든 메서드 조인 포인트
		pointcut.setExpression("@within(com.cooper.springaop.annotation.CooperTransactional)");
		WithInAnnotationLogTraceAdvice advice = new WithInAnnotationLogTraceAdvice();
		//advisor = pointcut + advice
		return new DefaultPointcutAdvisor(pointcut, advice);
	}
}
