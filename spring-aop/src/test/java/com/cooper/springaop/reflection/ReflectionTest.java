package com.cooper.springaop.reflection;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ReflectionTest {

	@Test
	void reflection01() throws Exception {
		Hello target = new Hello();

		//공통 로직1 시작
		log.info("start");
		String result1 = target.callA();
		log.info("result={}", result1);
		//공통 로직1 종료

		//공통 로직2 시작
		log.info("start");
		String result2 = target.callB();
		log.info("result={}", result2);
		//공통 로직2 종료
	}

	@Test
	void reflection02() throws Exception {
		/**
		 * 내부 클래스 구분을 위해 $ 를 추가한다.
		 */
		Class helloClass = Class.forName("com.cooper.springaop.reflection.ReflectionTest$Hello");

		// invoke callA
		Hello target = new Hello();
		Method methodCallA = helloClass.getMethod("callA");
		Object result1 = methodCallA.invoke(target);
		log.info("result={}", result1);

		log.info("");

		// invoke callB
		Method methodCallB = helloClass.getMethod("callB");
		Object result2 = methodCallB.invoke(target);
		log.info("result={}", result2);
	}

	/**
	 * 리플렉션을 사용하면 클래스와 메서드의 메타정보를 사용해서 애플리케이션을 동적으로 유연하게 만들 수 있다.
	 * <br>
	 * 하지만 리플렉션 기술은 런타임에 동작하기 때문에 컴파일 시점에 에러를 잡을 수 없다.
	 */
	@Test
	void reflection03() throws Exception {
		Class helloClass = Class.forName("com.cooper.springaop.reflection.ReflectionTest$Hello");

		// invoke callA
		Hello target = new Hello();
		Method methodCallA = helloClass.getMethod("callA");
		Object result1 = methodCallA.invoke(target);
		log.info("result={}", result1);

		log.info("");

		// invoke callB
		Method methodCallB = helloClass.getMethod("callB");
		Object result = methodCallB.invoke(target);
		log.info("result={}", result);
	}

	@Slf4j
	static class Hello {

		public String callA() {
			log.info("callA");
			return "A";
		}

		public String callB() {
			log.info("callB");
			return "B";
		}
	}
}
