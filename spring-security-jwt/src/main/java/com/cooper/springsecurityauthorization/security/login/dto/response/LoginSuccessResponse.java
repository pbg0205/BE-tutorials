package com.cooper.springsecurityauthorization.security.login.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Builder
public class LoginSuccessResponse {
	private final String accessToken;
}
