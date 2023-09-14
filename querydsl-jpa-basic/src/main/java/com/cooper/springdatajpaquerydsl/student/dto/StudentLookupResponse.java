package com.cooper.springdatajpaquerydsl.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class StudentLookupResponse {
    private Long id;
    private String name;
    private String tagName;
    private List<AwardLookupResponse> awards;

}
