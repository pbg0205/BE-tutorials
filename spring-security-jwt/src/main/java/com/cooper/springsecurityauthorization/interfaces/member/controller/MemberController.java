package com.cooper.springsecurityauthorization.interfaces.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	@GetMapping("/api/members")
	public String getMember() {
		return "user access success";
	}

	@GetMapping("/api/admin")
	public String getAdmin() {
		return "admin access success";
	}
}
