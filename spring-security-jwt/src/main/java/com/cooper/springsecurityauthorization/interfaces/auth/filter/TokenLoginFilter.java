package com.cooper.springsecurityauthorization.interfaces.auth.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cooper.springsecurityauthorization.domain.auth.dto.LoginTrialAuthenticationToken;
import com.cooper.springsecurityauthorization.interfaces.auth.dto.request.LoginRequest;

public class TokenLoginFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	private ObjectMapper objectMapper;

	public  TokenLoginFilter(final String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(
		final HttpServletRequest request, final HttpServletResponse response)
		throws AuthenticationException, IOException {

		if (!isContentTypeJson(request.getHeader(HttpHeaders.CONTENT_TYPE))) {
			throw new IllegalStateException("content type header type is not supported");
		}

		final LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
		if (isLoginRequestEmpty(loginRequest)) {
			throw new IllegalArgumentException("email or password is empty");
		}

		final LoginTrialAuthenticationToken loginTrialAuthenticationToken =
			LoginTrialAuthenticationToken.create(loginRequest.email(), loginRequest.password());

		return getAuthenticationManager().authenticate(loginTrialAuthenticationToken);
	}

	private boolean isLoginRequestEmpty(final LoginRequest loginRequest) {
		return ObjectUtils.isEmpty(loginRequest.email()) || ObjectUtils.isEmpty(loginRequest.password());
	}

	private boolean isContentTypeJson(final String contentTypeHeader) {
		return (contentTypeHeader != null) && contentTypeHeader.contains(MediaType.APPLICATION_JSON_VALUE);
	}
}
