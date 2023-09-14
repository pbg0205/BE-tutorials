package com.cooper.springdatajpaquerydsl.student.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AwardAssignRequest {
    private Long studentId;
    private Long awardId;
}
