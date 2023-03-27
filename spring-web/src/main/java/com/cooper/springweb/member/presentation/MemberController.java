package com.cooper.springweb.member.presentation;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.cooper.springweb.member.domain.Member;
import com.cooper.springweb.member.dto.LoginRequestDto;
import com.cooper.springweb.member.dto.SignupRequestDto;
import com.cooper.springweb.member.dto.SignupResponseDto;
import com.cooper.springweb.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private static final String MEMBER_KEY = "memberKey";
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
		SignupResponseDto signupResponseDto = memberService.signup(signupRequestDto);
		return ResponseEntity.ok(signupResponseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession httpSession) {
		Member loginMember = memberService.login(loginRequestDto);
		httpSession.setAttribute(MEMBER_KEY, loginMember);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Void> accessMember(@SessionAttribute(name = MEMBER_KEY) Member member) {
		log.info("member : {}", member);
		return ResponseEntity.ok().build();
	}

}
