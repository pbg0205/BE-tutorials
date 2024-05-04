package com.cooper.springhateoas.student.business;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.cooper.springhateoas.student.domain.Student;
import com.cooper.springhateoas.student.dto.StudentCreateRequest;
import com.cooper.springhateoas.student.dto.StudentCreateResponse;
import com.cooper.springhateoas.student.dto.StudentDetailResponse;
import com.cooper.springhateoas.student.persistence.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final AtomicLong idGenerator = new AtomicLong();

	public StudentCreateResponse createStudent(final StudentCreateRequest studentCreateRequest) {
		final Long studentId = idGenerator.getAndIncrement();
		Student student = new Student(
			studentId,
			studentCreateRequest.getName(),
			studentCreateRequest.getPhoneNumber(),
			studentCreateRequest.getBirthDate(),
			studentCreateRequest.getSchoolId());

		Student savedStudent = studentRepository.save(student);

		return new StudentCreateResponse(savedStudent.getId(), savedStudent.getName());
	}

	public StudentDetailResponse findByStudentId(final Long studentId) {
		Student student = studentRepository.findByStudentId(studentId).orElseThrow(IllegalArgumentException::new);
		return new StudentDetailResponse(
			student.getId(),
			student.getName(),
			student.getPhoneNumber(),
			student.getBirthDate(),
			student.getSchoolId()
		);
	}
}
