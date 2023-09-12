package com.cooper.springdatajpaquerydsl.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentCreateResponse {
    private Long id;
    private String name;
    private String tagName;
}
