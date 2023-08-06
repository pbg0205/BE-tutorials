package com.cooper.springdatajpabasic.employee.controller;

import com.cooper.springdatajpabasic.employee.domain.Employee;
import com.cooper.springdatajpabasic.employee.dto.EmployeeDto;
import com.cooper.springdatajpabasic.employee.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmployeesController {

    private final EmployeesService employeesService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> findEmployeesByDepartmentIds(String departmentIdsInput) {
        List<Long> departmentIds = Arrays.stream(departmentIdsInput.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeesService.findEmployeesByDepartmentIds(departmentIds));
    }

}
