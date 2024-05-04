package com.cooper.springhateoas.student.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudentCreateResponse {
	private final Long studentId;
	private final String name;
}
