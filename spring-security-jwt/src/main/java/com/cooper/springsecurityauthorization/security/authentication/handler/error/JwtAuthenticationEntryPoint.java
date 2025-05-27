package com.cooper.springsecurityauthorization.security.authentication.handler.error;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cooper.springsecurityauthorization.api.support.error.CommonErrorType;
import com.cooper.springsecurityauthorization.api.support.response.ApiResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
		final AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(HttpHeaders.CONTENT_TYPE);
		response.setCharacterEncoding(request.getCharacterEncoding());

		CommonErrorType commonErrorType = CommonErrorType.FORBIDDEN;
		final ApiResponse<?> apiResponse =
			ApiResponse.error(commonErrorType.getErrorCode().name(), commonErrorType.getMessage());

		objectMapper.writeValue(response.getWriter(), apiResponse);
	}
}
