package com.cooper.virtualthreadwithspring.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	@DisplayName("virtual thread with db test")
	void testDbQuery() {
		long startTime = System.currentTimeMillis();
		for (int i = 1; i <= 100_000; i++) {
			testRestTemplate.getForObject("/query", String.class);
			if (i % 1000 == 0) {
				log.info("count : {}", i);
			}
		}
		long endTime = System.currentTimeMillis();

		log.info("elapsed time : {} ms", endTime - startTime);
	}

}
