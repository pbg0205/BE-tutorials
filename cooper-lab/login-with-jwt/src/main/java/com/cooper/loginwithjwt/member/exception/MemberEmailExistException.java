package com.cooper.loginwithjwt.member.exception;

import com.cooper.loginwithjwt.common.error.ErrorType;
import lombok.Getter;

@Getter
public class MemberEmailExistException extends IllegalArgumentException {

    private final ErrorType errorType;

    private final Object data;

    public MemberEmailExistException(String message, ErrorType errorType, String data) {
        super(message);
        this.errorType = errorType;
        this.data = data;
    }

}
