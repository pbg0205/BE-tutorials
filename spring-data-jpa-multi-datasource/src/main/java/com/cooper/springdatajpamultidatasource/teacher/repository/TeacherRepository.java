package com.cooper.springdatajpamultidatasource.teacher.repository;

import com.cooper.springdatajpamultidatasource.teacher.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
