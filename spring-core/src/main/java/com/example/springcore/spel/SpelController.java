package com.example.springcore.spel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SpelController {

	@GetMapping("/{test}")
	@SpelContext(id = "#test")
	public String findBy(@PathVariable String test) {
		log.debug("test : {}", test);
		log.debug("tenantContext : {}", TenantContext.getTenantId());
		return "OK";
	}
}
