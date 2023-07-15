package com.cooper.loginwithjwt.member.presentation;

import com.cooper.loginwithjwt.common.response.ApiResponse;
import com.cooper.loginwithjwt.member.application.MemberService;
import com.cooper.loginwithjwt.member.dto.MemberCreateRequestDto;
import com.cooper.loginwithjwt.member.dto.MemberCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/v1/members")
    public ApiResponse<MemberCreateResponseDto> createMember(@RequestBody MemberCreateRequestDto memberCreateRequestDto) {
        MemberCreateResponseDto memberCreateResponseDto = memberService.createMember(memberCreateRequestDto);
        return ApiResponse.success(memberCreateResponseDto);
    }

}
