package com.cooper.loginwithjwt.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreateRequestDto {

    private String email;
    private String password;
    private String name;
    private String nickname;

}
