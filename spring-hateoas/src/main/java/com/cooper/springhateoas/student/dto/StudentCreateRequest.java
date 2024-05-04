package com.cooper.springhateoas.student.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class StudentCreateRequest {
	private final String name;
	private final String phoneNumber;
	private final LocalDate birthDate;
	private final String schoolId;
}
