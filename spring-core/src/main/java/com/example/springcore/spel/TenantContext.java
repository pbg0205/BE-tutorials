package com.example.springcore.spel;

import lombok.Getter;

@Getter
public class TenantContext {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

	public static void setTenantId(String tenantId) {
		contextHolder.set(tenantId);
	}

	public static String getTenantId() {
		return contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}
