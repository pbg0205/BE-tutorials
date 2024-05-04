package com.cooper.springhateoas.student.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cooper.springhateoas.student.domain.Student;

@Repository
public class StudentRepository {

	private final Map<Long, Student> studentDb;

	public StudentRepository() {
		this.studentDb = new HashMap<>();
	}

	public Student save(final Student student) {
		this.studentDb.put(student.getId(), student);
		return this.studentDb.get(student.getId());
	}

	public Optional<Student> findByStudentId(final Long studentId) {
		return Optional.ofNullable(studentDb.get(studentId));
	}
}
