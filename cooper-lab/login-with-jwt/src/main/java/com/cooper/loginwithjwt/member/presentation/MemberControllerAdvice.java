package com.cooper.loginwithjwt.member.presentation;

import com.cooper.loginwithjwt.common.error.ErrorType;
import com.cooper.loginwithjwt.common.response.ApiResponse;
import com.cooper.loginwithjwt.member.exception.MemberEmailExistException;
import com.cooper.loginwithjwt.member.exception.MemberEmailNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(MemberEmailExistException.class)
    public ResponseEntity<ApiResponse<?>> handleMemberEmailExistException(MemberEmailExistException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR: log.error("MemberException : {}", e.getMessage(), e); break;
            case WARN: log.warn("MemberException : {}", e.getMessage(), e); break;
            default: log.info("MemberException : {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

    @ExceptionHandler(MemberEmailNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleMemberEmailNotFoundException(MemberEmailNotFoundException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR: log.error("MemberException : {}", e.getMessage(), e); break;
            case WARN: log.warn("MemberException : {}", e.getMessage(), e); break;
            default: log.info("MemberException : {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
    }

}
