package com.cooper.springdatajpabasic.employee.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeDto {

    private final Long employeeId;
    private final String employeeName;
    private final Long departmentId;
    private final String departmentName;

}
