package com.cooper.springhateoas.student.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Student {

	private Long id;
	private String name;
	private String phoneNumber;
	private LocalDate birthDate;
	private String schoolId;


}
