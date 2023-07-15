package com.cooper.loginwithjwt.member.exception;

import com.cooper.loginwithjwt.common.error.ErrorType;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Getter
public class MemberEmailNotFoundException extends UsernameNotFoundException {

    private final ErrorType errorType;

    private final Object data;

    public MemberEmailNotFoundException(String message, ErrorType errorType, String data) {
        super(message);
        this.errorType = errorType;
        this.data = data;
    }

}
