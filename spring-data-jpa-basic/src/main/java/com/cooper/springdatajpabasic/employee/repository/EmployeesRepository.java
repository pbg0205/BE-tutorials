package com.cooper.springdatajpabasic.employee.repository;

import com.cooper.springdatajpabasic.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query("select employee " +
            "from Employee employee " +
            "inner join Department department on employee.department.id = department.id " +
            "where department.id in :departmentIds")
    List<Employee> findEmployeesByDepartmentIdNoFetch(List<Long> departmentIds);

    @Query("select employee " +
            "from Employee employee " +
            "inner join fetch employee.department " +
            "where employee.department.id in :departmentIds")
    List<Employee> findEmployeesByDepartmentIdsWithFetch(List<Long> departmentIds);

    @Query("select employee " +
            "from Employee employee " +
            "inner join fetch Department department on employee.department.id = department.id " +
            "where department.id in :departmentIds")
    List<Employee> findEmployeesByDepartmentIdsNormalJoin(List<Long> departmentIds);

}
