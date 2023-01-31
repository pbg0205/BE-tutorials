package com.cooper.springdatajpa.student.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {
}
