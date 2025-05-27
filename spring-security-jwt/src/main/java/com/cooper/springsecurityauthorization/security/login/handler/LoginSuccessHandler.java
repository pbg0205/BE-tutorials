package com.cooper.springsecurityauthorization.security.login.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cooper.springsecurityauthorization.security.JwtProvider;
import com.cooper.springsecurityauthorization.security.MemberAuthDetails;
import com.cooper.springsecurityauthorization.security.login.dto.response.LoginSuccessResponse;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
		final Authentication authentication) throws IOException {
		final MemberAuthDetails memberAuthDetails = (MemberAuthDetails)authentication.getPrincipal();

		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final LoginSuccessResponse loginSuccessResponse = LoginSuccessResponse.builder()
			.accessToken(jwtProvider.createAccessToken(memberAuthDetails.getId(),
				memberAuthDetails.getEmail(),
				memberAuthDetails.getAuthorityAsStr()))
			.build();

		objectMapper.writeValue(response.getWriter(), loginSuccessResponse);
	}
}
