package com.cooper.springsecurityauthorization.domain.auth.dto;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class LoginSuccessAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	private LoginSuccessAuthenticationToken(
		final Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		setAuthenticated(true);
	}

	public static LoginSuccessAuthenticationToken create(
		final Object principal, Collection<? extends GrantedAuthority> authorities) {
		return new LoginSuccessAuthenticationToken(principal, authorities);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}
}
