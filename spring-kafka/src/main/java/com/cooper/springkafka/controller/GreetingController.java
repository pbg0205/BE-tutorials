package com.cooper.springkafka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.cooper.springkafka.dto.Greeting;
import com.cooper.springkafka.producer.GreetingProducer;

@RestController
@RequiredArgsConstructor
public class GreetingController {

	private final GreetingProducer greetingProducer;

	@PostMapping("/greeting")
	public String greeting(@RequestBody Greeting greeting) {
		greetingProducer.sendMessage(greeting);
		return "ok";
	}
}
