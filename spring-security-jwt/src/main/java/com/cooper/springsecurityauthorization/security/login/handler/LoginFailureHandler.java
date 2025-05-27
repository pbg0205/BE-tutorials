package com.cooper.springsecurityauthorization.security.login.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cooper.springsecurityauthorization.api.support.error.CommonErrorType;
import com.cooper.springsecurityauthorization.api.support.response.ApiResponse;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
		final AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(HttpHeaders.CONTENT_TYPE);
		response.setCharacterEncoding(request.getCharacterEncoding());

		CommonErrorType errorType = CommonErrorType.UNAUTHORIZED;
		ApiResponse<?> apiResponse = ApiResponse.error(errorType.getErrorCode().name(), errorType.getMessage());

		objectMapper.writeValue(response.getWriter(), apiResponse);
	}
}
