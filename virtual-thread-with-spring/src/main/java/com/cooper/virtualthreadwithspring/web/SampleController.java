package com.cooper.virtualthreadwithspring.web;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SampleController {

	private final JdbcTemplate jdbcTemplate;

	@GetMapping("/")
	public String getThreadName() {
		// 단순히 스레드 이름을 반환 아무런 blocking 코드 없음
		return Thread.currentThread().toString();
	}

	@GetMapping("/block")
	public String getBlockedResponse() throws InterruptedException {
		// Thread sleep 1초
		// 비지니스 로직 처리에 thread 가 blocking 되는 환경 가정
		Thread.sleep(1000);
		return "OK";
	}

	@GetMapping("/query")
	public String queryAndReturn() {
		// 쿼리 질의가 1초 걸린다고 가정
		return jdbcTemplate.queryForList("select sleep(1);").toString();
	}
}
