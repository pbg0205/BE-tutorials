package com.cooper.loginwithjwt.member.application;

import com.cooper.loginwithjwt.member.dto.MemberCreateRequestDto;
import com.cooper.loginwithjwt.member.dto.MemberCreateResponseDto;

public interface MemberService {

    MemberCreateResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto);
}
