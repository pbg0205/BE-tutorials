package com.cooper.springaop.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping("/v1/request")
	public String request(@RequestParam("itemId") String itemId) {
		return orderService.order(itemId, "item" + itemId);
	}

	@GetMapping("/v1/no-log")
	String noLog() {
		return "ok";
	}
}
