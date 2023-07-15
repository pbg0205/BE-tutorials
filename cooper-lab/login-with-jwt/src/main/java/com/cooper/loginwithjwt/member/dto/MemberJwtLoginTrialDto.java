package com.cooper.loginwithjwt.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberJwtLoginTrialDto {

    private String email;
    private String password;

}
