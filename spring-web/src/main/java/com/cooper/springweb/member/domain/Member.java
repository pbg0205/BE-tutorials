package com.cooper.springweb.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Member {

	private String id;
	private String userId;
	private String password;

	private Member(String id, String userId, String password) {
		this.id = id;
		this.userId = userId;
		this.password = password;
	}

	public static Member create(String id, String userId, String password) {
		return new Member(id, userId, password);
	}

	public boolean matchesPassword(String password) {
		return this.password.equals(password);
	}
}
