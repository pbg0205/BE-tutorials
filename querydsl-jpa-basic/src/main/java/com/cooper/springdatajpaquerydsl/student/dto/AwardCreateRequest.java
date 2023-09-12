package com.cooper.springdatajpaquerydsl.student.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AwardCreateRequest {
    private Long id;
    private String name;
}
