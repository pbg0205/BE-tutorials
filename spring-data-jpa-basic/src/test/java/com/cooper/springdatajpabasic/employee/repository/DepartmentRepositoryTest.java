package com.cooper.springdatajpabasic.employee.repository;

import com.cooper.springdatajpabasic.annotation.RepositorySlicingTest;
import com.cooper.springdatajpabasic.employee.domain.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RepositorySlicingTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("부서의 연관관계의 직원을 호출하면 N + 1 문제가 발생한다 + @BatchSize 테스트")
    void findEmployeeInDepartment() {
        List<Department> departments = departmentRepository.findAll();

        departments.get(0).getEmployees().size();
        departments.get(1).getEmployees().size();
        departments.get(2).getEmployees().size();
        departments.get(3).getEmployees().size();
        departments.get(4).getEmployees().size();
        departments.get(5).getEmployees().size();
        departments.get(6).getEmployees().size();
        departments.get(7).getEmployees().size();
    }
}
