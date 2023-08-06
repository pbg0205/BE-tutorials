package com.cooper.springdatajpabasic.employee.service;

import com.cooper.springdatajpabasic.employee.domain.Employee;
import com.cooper.springdatajpabasic.employee.dto.EmployeeDto;
import com.cooper.springdatajpabasic.employee.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    public List<EmployeeDto> findEmployeesByDepartmentIds(List<Long> departmentIds) {
        List<Employee> employees = employeesRepository.findEmployeesByDepartmentIdsWithFetch(departmentIds);
        return employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getName(),
                        employee.getDepartment().getId(),
                        employee.getDepartment().getName()))
                .collect(Collectors.toList());
    }
}
