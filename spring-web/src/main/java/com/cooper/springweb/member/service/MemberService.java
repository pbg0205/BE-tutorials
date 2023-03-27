package com.cooper.springweb.member.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cooper.springweb.member.domain.Member;
import com.cooper.springweb.member.dto.LoginRequestDto;
import com.cooper.springweb.member.dto.SignupRequestDto;
import com.cooper.springweb.member.dto.SignupResponseDto;
import com.cooper.springweb.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
		Member member = Member.create(
		  UUID.randomUUID().toString(),
		  signupRequestDto.getUserId(),
		  signupRequestDto.getPassword()
		);

		Member savedMember = memberRepository.save(member);

		return new SignupResponseDto(savedMember.getUserId(), savedMember.getPassword());
	}

	public Member login(LoginRequestDto loginRequestDto) {
		String userId = loginRequestDto.getUserId();
		String password = loginRequestDto.getPassword();

		Member member = memberRepository.findById(userId)
		  .orElseThrow(RuntimeException::new);

		if (!member.matchesPassword(password)) {
			throw new RuntimeException();
		}

		return member;
	}

}
