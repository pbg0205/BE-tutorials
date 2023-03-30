package com.cooper.springweb.member.presentation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cooper.springweb.member.dto.MemberRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController2 {

	@GetMapping("/params")
	public ResponseEntity<Void> withParam(MemberRequestDto memberRequestDto, HttpServletRequest httpServletRequest) {
		log.info("request url: {}", httpServletRequest.getRequestURL());
		log.info("query string: {}", httpServletRequest.getQueryString());
		log.info("memberRequestDto : {}", memberRequestDto);
		log.info("name is null ? {}", memberRequestDto.getName() == null);
		return ResponseEntity.ok().build();
	}

}
