package com.cooper.springhateoas.student.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudentGradeResponse {
	private final String name;
	private final Integer korean;
	private final Integer math;
	private final Integer english;
}
