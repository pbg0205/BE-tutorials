package com.cooper.springsecurityauthorization.security.authentication.handler.error;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cooper.springsecurityauthorization.api.support.error.CommonErrorType;
import com.cooper.springsecurityauthorization.api.support.response.ApiResponse;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void handle(final HttpServletRequest request, final HttpServletResponse response,
		final AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(HttpHeaders.CONTENT_TYPE);
		response.setCharacterEncoding(request.getCharacterEncoding());

		CommonErrorType errorType = CommonErrorType.FORBIDDEN;
		ApiResponse<?> apiResponse =
			ApiResponse.error(errorType.getErrorCode().name(), errorType.getMessage());

		objectMapper.writeValue(response.getWriter(), apiResponse);

	}
}
