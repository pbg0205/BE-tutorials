package com.cooper.springsecurityauthorization.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.cooper.springsecurityauthorization.member.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/api/members")
	public String getMember() {
		return "user access success";
	}

	@GetMapping("/api/admin")
	public String getAdmin() {
		return "admin access success";
	}
}
