package com.cooper.springsecurityauthorization.common.api.support.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorType {
	ERROR_UNKNOWN(CommonErrorCode.ERROR_COMMON01, "알 수 없는 예외가 발생했습니다."),
	INVALID_REQUEST_ARGUMENT(CommonErrorCode.ERROR_COMMON02, "유효하지 않는 입력 값을 포함하고 있습니다."),
	FORBIDDEN(CommonErrorCode.ERROR_COMMON03, "올바르지 못한 권한 요청입니다."),
	UNAUTHORIZED(CommonErrorCode.ERROR_COMMON04, "올바르지 못한 인증 요청입니다.");

	private final CommonErrorCode errorCode;
	private final String message;
}
