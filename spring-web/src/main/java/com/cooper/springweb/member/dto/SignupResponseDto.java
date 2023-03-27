package com.cooper.springweb.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupResponseDto {

	private final String userId;
	private final String password;

}
