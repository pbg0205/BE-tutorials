package com.cooper.springsecurityauthorization.domain.auth.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberAuthDetails implements UserDetails {

	private final Long id;
	private final String email;
	private final String password;
	private final GrantedAuthority authority;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(authority);
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public String getAuthorityAsStr() {
		return this.authority.getAuthority();
	}
}
