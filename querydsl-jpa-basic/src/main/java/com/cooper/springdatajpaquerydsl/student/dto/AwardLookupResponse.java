package com.cooper.springdatajpaquerydsl.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class AwardLookupResponse {
    private final Long id;
    private final String name;

}
