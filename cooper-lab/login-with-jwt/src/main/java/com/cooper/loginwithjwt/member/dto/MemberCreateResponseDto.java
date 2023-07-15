package com.cooper.loginwithjwt.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateResponseDto {

    private final String username;
    private final String name;

}
