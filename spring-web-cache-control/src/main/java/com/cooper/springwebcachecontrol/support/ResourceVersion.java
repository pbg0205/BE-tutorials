package com.cooper.springwebcachecontrol.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ResourceVersion {

	private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	private String version;

	@PostConstruct
	public void init() {
		this.version = now();
	}

	public String getVersion() {
		return version;
	}

	private static String now() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
		return LocalDateTime.now().format(formatter);
	}
}
