package com.cooper.springdatajpamultidatasource.student.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentUpdateRequestDto {

    private Long id;
    private String name;
    private int classNumber;

}
