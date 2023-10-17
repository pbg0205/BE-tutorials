package com.example.springcore.spel;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Aspect
@Slf4j
public class SpelAspect {

	private final ExpressionParser parser = new SpelExpressionParser();

	@Around("@annotation(com.example.springcore.spel.SpelContext)")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();

		SpelContext annotation = method.getAnnotation(SpelContext.class);
		String id = (String) getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.id());

		log.debug("aspect id: {}", id);

		try {
			TenantContext.setTenantId(id);
			log.debug("TenantContext created");
			return joinPoint.proceed();
		} finally {
			TenantContext.clear();
			log.debug("TenantContext deleted");
		}
	}

	private Object getDynamicValue(String[] parameterNames, Object[] args, String id) {
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < parameterNames.length; i++) {
			log.debug("[{}] parameterName : {}, arg : {}", i, parameterNames[i], args[i]);
			context.setVariable(parameterNames[i], args[i]);
		}

		return parser.parseExpression(id).getValue(context, Object.class);
	}
}
