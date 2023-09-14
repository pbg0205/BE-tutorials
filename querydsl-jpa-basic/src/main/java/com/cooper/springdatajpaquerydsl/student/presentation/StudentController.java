package com.cooper.springdatajpaquerydsl.student.presentation;

import com.cooper.springdatajpaquerydsl.student.business.StudentService;
import com.cooper.springdatajpaquerydsl.student.domain.Student;
import com.cooper.springdatajpaquerydsl.student.dto.AwardAssignRequest;
import com.cooper.springdatajpaquerydsl.student.dto.AwardCreateRequest;
import com.cooper.springdatajpaquerydsl.student.dto.AwardCreateResponse;
import com.cooper.springdatajpaquerydsl.student.dto.StudentCreateRequest;
import com.cooper.springdatajpaquerydsl.student.dto.StudentCreateResponse;
import com.cooper.springdatajpaquerydsl.student.dto.StudentLookupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/v1/students")
    public ResponseEntity<StudentCreateResponse> createStudent(@RequestBody StudentCreateRequest studentCreateRequest) {
        StudentCreateResponse studentCreateResponse = studentService.createStudent(studentCreateRequest);
        return ResponseEntity.ok(studentCreateResponse);
    }

    @GetMapping("/v1/students")
    public ResponseEntity<List<Student>> findByTagNames(@RequestParam List<String> tagNames) {
        List<Student> students = studentService.findByTagNames(tagNames);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/v2/students")
    public ResponseEntity<List<StudentLookupResponse>> findAllByIds(@RequestParam List<Long> studentIds) {
        List<StudentLookupResponse> students = studentService.findByStudentIds(studentIds);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/v1/students/award")
    public ResponseEntity<AwardCreateResponse> createAward(@RequestBody AwardCreateRequest awardCreateRequest) {
        AwardCreateResponse awardCreateResponse = studentService.createAward(awardCreateRequest);
        return ResponseEntity.ok(awardCreateResponse);
    }

    @PutMapping("/v1/students/award/assign")
    public ResponseEntity<Void> assignAward(@RequestBody AwardAssignRequest awardAssignRequest) {
        studentService.assignAward(awardAssignRequest);
        return ResponseEntity.ok(null);
    }

}
