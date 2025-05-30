package com.cooper.springsecurityauthorization.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cooper.springsecurityauthorization.domain.member.dto.MemberAuthDetails;
import com.cooper.springsecurityauthorization.domain.member.dto.LoginSuccessAuthenticationToken;
import com.cooper.springsecurityauthorization.domain.member.dto.LoginTrialAuthenticationToken;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final String email = authentication.getName();
		final String password = (String)authentication.getCredentials();

		final MemberAuthDetails memberAuthDetails = (MemberAuthDetails) userDetailsService.loadUserByUsername(email);

		if (memberAuthDetails == null) {
			throw new UsernameNotFoundException("email is not correct");
		}

		if (!passwordEncoder.matches(password, memberAuthDetails.getPassword())) {
			throw new BadCredentialsException("password is not correct");
		}

		return LoginSuccessAuthenticationToken.create(memberAuthDetails, memberAuthDetails.getAuthorities());
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(LoginTrialAuthenticationToken.class);
	}
}
