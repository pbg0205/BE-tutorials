package com.cooper.springdatajpaquerydslpagination.employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeesRepositoryTest {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Test
    void testFindById() {
        //given, when
        long startMillis = System.currentTimeMillis();
        Employees employee = employeesRepository.findById(10001L);
        long endMillis = System.currentTimeMillis();
        long elapsedMillis = endMillis - startMillis;

        System.out.println("elapsedMillis = " + elapsedMillis);

        //then
        assertThat(employee.getFirstName()).isEqualTo("Georgi");
    }

    @Test
    void findOffsetBasedPagination() {
        //given
        List<Employees> employees = employeesRepository.findOffsetBasedPagination(10, 276525L);

        //then
        assertThat(employees).hasSize(10);

    }

    @Test
    void findByCursorBasedPagination() {
        List<Employees> employees = employeesRepository.findCursorBasedPagination(10, 476500L);

        //then
        assertThat(employees).hasSize(10);
    }

}
