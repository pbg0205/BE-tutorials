package com.cooper.springsecurityauthorization.domain.auth.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class LoginTrialAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private final Object credentials;

	private LoginTrialAuthenticationToken(final Object principal, final Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public static LoginTrialAuthenticationToken create(final Object principal, final Object credentials) {
		return new LoginTrialAuthenticationToken(principal, credentials);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

}
