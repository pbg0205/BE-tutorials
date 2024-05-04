package com.cooper.springhateoas.student.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudentDetailResponse extends RepresentationModel<StudentDetailResponse> {
	private final Long id;
	private final String name;
	private final String phoneNumber;
	private final LocalDate birthDate;
	private final String schoolId;
}
