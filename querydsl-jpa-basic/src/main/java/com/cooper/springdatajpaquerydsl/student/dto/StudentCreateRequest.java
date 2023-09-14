package com.cooper.springdatajpaquerydsl.student.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentCreateRequest {
    private String name;
    private String tagName;
}
