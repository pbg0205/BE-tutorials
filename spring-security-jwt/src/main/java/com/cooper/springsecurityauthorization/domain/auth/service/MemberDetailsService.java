package com.cooper.springsecurityauthorization.domain.auth.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.cooper.springsecurityauthorization.domain.member.model.Member;
import com.cooper.springsecurityauthorization.domain.member.repository.MemberRepository;
import com.cooper.springsecurityauthorization.domain.auth.dto.MemberAuthDetails;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Member member = memberRepository.findByEmail(username);

		return new MemberAuthDetails(
			member.getId(),
			member.getEmail(),
			passwordEncoder.encode(member.getPassword()),
			new SimpleGrantedAuthority(member.getRole()));
	}
}
