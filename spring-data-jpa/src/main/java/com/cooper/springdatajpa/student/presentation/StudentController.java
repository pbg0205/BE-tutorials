package com.cooper.springdatajpa.student.presentation;

import com.cooper.springdatajpa.student.business.StudentService;
import com.cooper.springdatajpa.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/v1/students")
    public ResponseEntity<List<Student>> findByTagNames(@RequestParam List<String> tagNames) {
        List<Student> students = studentService.findByTagNames(tagNames);
        return ResponseEntity.ok(students);
    }

}
