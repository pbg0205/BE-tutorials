package com.cooper.springdatajpamultidatasource.student.presentation;

import com.cooper.springdatajpamultidatasource.student.dto.StudentCreateRequestDto;
import com.cooper.springdatajpamultidatasource.student.dto.StudentUpdateRequestDto;
import com.cooper.springdatajpamultidatasource.student.business.StudentService;
import com.cooper.springdatajpamultidatasource.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody StudentCreateRequestDto studentCreateRequestDto) {
        Long savedStudentId = studentService.save(studentCreateRequestDto);

        URI createdUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{studentId}")
                .buildAndExpand(savedStudentId)
                .toUri();

        return ResponseEntity.created(createdUri).build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> findById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.findById(studentId));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody StudentUpdateRequestDto studentUpdateRequestDto) {
        return ResponseEntity.ok(studentService.update(studentUpdateRequestDto));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.delete(studentId);
        return ResponseEntity.noContent().build();
    }


}
