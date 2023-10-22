package com.cooper.springaop.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

	private Map<String, String> repository = new HashMap<>();

	public String save(String key, String value) {
		repository.put(key, value);
		return repository.get(key);
	}
}
