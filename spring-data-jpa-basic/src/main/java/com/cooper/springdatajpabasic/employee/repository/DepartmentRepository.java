package com.cooper.springdatajpabasic.employee.repository;

import com.cooper.springdatajpabasic.employee.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
