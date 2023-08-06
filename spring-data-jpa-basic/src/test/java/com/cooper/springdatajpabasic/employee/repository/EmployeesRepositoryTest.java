package com.cooper.springdatajpabasic.employee.repository;

import com.cooper.springdatajpabasic.annotation.RepositorySlicingTest;
import com.cooper.springdatajpabasic.employee.domain.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositorySlicingTest
class EmployeesRepositoryTest {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Test
    @DisplayName("페이지네이션을 적용할 경우, 인메모리에서 처리된다")
    void findAllPaging() {
        Page<Employee> employees = employeesRepository.findAll(PageRequest.of(0, 10));
        assertThat(employees).hasSize(10);
    }

    @Test
    @DisplayName("Lazy 설정을 하더라도 연관관계의 엔티티를 조회하면 N + 1 문제가 발생한다")
    void findEmployeesByDepartmentIdsNoFetch() {
        //given, when
        List<Employee> employees = employeesRepository.findEmployeesByDepartmentIdNoFetch(List.of(11L, 12L, 13L));

        for (Employee employee : employees) {
            System.out.println("employee.getDepartment() = " + employee.getDepartment());
        }

        //then
        assertThat(employees).hasSize(200 / 8 * 3);
    }

    @Test
    @DisplayName("Lazy 설정을 하고 fetch join 연관관계의 엔티티를 조회하면 N + 1 문제가 발생하지 않는다")
    void findEmployeesByDepartmentIdsWithFetch() {
        //given, when
        List<Employee> employees = employeesRepository.findEmployeesByDepartmentIdsWithFetch(List.of(11L, 12L, 13L));

        for (Employee employee : employees) {
            System.out.println("employee.getDepartment() = " + employee.getDepartment());
        }

        //then
        assertThat(employees).hasSize(200 / 8 * 3);
    }

    @Test
    @DisplayName("일반 inner join 의 연관관계의 엔티티를 조회하면 N + 1 문제가 발생한다")
    void findEmployeesByDepartmentIdsNormalJoin() {
        //given, when
        List<Employee> employees = employeesRepository.findEmployeesByDepartmentIdsNormalJoin(List.of(11L, 12L, 13L));

        for (Employee employee : employees) {
            System.out.println("employee.getDepartment() = " + employee.getDepartment());
        }

        //then
        assertThat(employees).hasSize(200 / 8 * 3);
    }

}

