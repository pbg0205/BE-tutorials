package com.cooper.springaop.api;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

	private HashMap<String, String> repository = new HashMap<>();

	public String save(String key, String value) {
		return repository.put(key, value);
	}
}
