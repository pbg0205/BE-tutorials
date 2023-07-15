package com.cooper.loginwithjwt.member.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberExceptionMessages {

    public static final String MEMBER_EMAIL_NOT_FOUND_MESSAGE = "해당 이메일의 유저를 찾을 수 없습니다.";
    public static final String MEMBER_EMAIL_EXIST_MESSAGE = "해당 이메일의 유저가 이미 존재합니다.";
}
