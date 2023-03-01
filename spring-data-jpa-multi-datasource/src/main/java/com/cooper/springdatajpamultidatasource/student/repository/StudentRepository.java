package com.cooper.springdatajpamultidatasource.student.repository;

import com.cooper.springdatajpamultidatasource.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
