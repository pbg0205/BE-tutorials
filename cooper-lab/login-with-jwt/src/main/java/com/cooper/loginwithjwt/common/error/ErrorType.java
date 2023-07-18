package com.cooper.loginwithjwt.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    DEFAULT_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCode.E500,
            "An unexpected error has occurred.",
            LogLevel.ERROR),
    BAD_REQUEST_ERROR(
            HttpStatus.BAD_REQUEST,
            ErrorCode.E400,
            "invalid input parameter.",
            LogLevel.ERROR),
    LOGIN_FAIL(
            HttpStatus.FORBIDDEN,
            ErrorCode.E40101,
            "login fail",
            LogLevel.ERROR),
    INVALID_PASSWORD(
            HttpStatus.FORBIDDEN,
            ErrorCode.E40102,
            "login fail because of invalid password.",
            LogLevel.ERROR),
    ACCOUNT_LOCK(
            HttpStatus.FORBIDDEN,
            ErrorCode.E40103,
            "login fail because account is locked.",
            LogLevel.ERROR);

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LogLevel logLevel;

}
