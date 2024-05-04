package com.cooper.springhateoas.student.presentation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.cooper.springhateoas.student.business.StudentService;
import com.cooper.springhateoas.student.dto.StudentCreateRequest;
import com.cooper.springhateoas.student.dto.StudentCreateResponse;
import com.cooper.springhateoas.student.dto.StudentDetailResponse;
import com.cooper.springhateoas.student.dto.StudentGradeResponse;
import com.cooper.springhateoas.support.ApiResponse;

@RestController
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@PostMapping(value = "/api/v1/high-schools/students")
	public ResponseEntity<ApiResponse<StudentCreateResponse>> createStudent(
		@RequestBody final StudentCreateRequest studentCreateRequest
	) {
		StudentCreateResponse studentCreateResponse = studentService.createStudent(studentCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(studentCreateResponse));
	}

	@GetMapping(
		value = "/api/v1/high-schools/{highSchoolId}/students/{studentId}",
		produces = {MediaTypes.HAL_JSON_VALUE}
	)
	public ResponseEntity<ApiResponse<StudentDetailResponse>> findByStudentId(
		@PathVariable final Long highSchoolId,
		@PathVariable final Long studentId
	) {
		StudentDetailResponse studentDetailResponse = studentService.findByStudentId(studentId);

		Link selfLink = linkTo(methodOn(StudentController.class).findByStudentId(highSchoolId, studentId)).withSelfRel();
		studentDetailResponse.add(selfLink);

		for (Long grade : List.of(9L, 10L, 11L)) {
			Link gradesLink = linkTo(methodOn(StudentController.class).findByStudentGrade(studentId, grade)).withRel(grade + " grade Link");
			studentDetailResponse.add(gradesLink);
		}

		// RepresentationModel<StudentDetailResponse> collectionModel = (RepresentationModel<StudentDetailResponse>)RepresentationModel.of(studentDetailResponse);
		return ResponseEntity.ok(ApiResponse.success(studentDetailResponse));
	}

	@GetMapping(value = "/api/v1/high-schools/{highSchoolId}/students/{studentId}/grades/{grade}")
	public ResponseEntity<ApiResponse<StudentGradeResponse>> findByStudentGrade(
		@PathVariable final Long studentId,
		@PathVariable final Long grade
	) {
		StudentDetailResponse studentDetailResponse = studentService.findByStudentId(studentId);
		StudentGradeResponse studentGradeResponse = new StudentGradeResponse(
			studentDetailResponse.getName(),
			(int)(grade * 100L),
			(int)(grade * 100),
			(int)(grade * 100L)
		);

		return ResponseEntity.ok(ApiResponse.success(studentGradeResponse));
	}
}
