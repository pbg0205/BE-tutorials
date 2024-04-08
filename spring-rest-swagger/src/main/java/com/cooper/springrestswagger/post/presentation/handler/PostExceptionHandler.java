package com.cooper.springrestswagger.post.presentation.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cooper.springrestswagger.post.dto.ErrorResponse;

@RestControllerAdvice
public class PostExceptionHandler {

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalException(IllegalArgumentException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponse(exception.getClass().getTypeName(), exception.getMessage()));
	}

}
