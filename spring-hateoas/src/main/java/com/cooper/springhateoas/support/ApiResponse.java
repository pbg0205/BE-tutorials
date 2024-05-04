package com.cooper.springhateoas.support;

public class ApiResponse<S> {

	private ResultType result;

	private S data;

	private String errorMessage;

	private ApiResponse(ResultType result, S data, final String errorMessage) {
		this.result = result;
		this.data = data;
		this.errorMessage = errorMessage;
	}

	public static ApiResponse<?> success() {
		return new ApiResponse<>(ResultType.SUCCESS, null, null);
	}

	public static <S> ApiResponse<S> success(S data) {
		return new ApiResponse<>(ResultType.SUCCESS, data, null);
	}

	public static ApiResponse<?> error(String errorMessage) {
		return new ApiResponse<>(ResultType.ERROR, null, errorMessage);
	}

	public ResultType getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

}
