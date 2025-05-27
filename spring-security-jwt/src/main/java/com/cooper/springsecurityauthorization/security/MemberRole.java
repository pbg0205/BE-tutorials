package com.cooper.springsecurityauthorization.security;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
	ANONYMOUS("ROLE_ANONYMOUS"),
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String roleName;

	public static MemberRole of(final String inputRole) {
		return Arrays.stream(values())
			.filter(role -> role.name().equals(inputRole))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("invalid role name"));
	}
}
