package com.cooper.springdatajpaquerydsl.student.business;

import com.cooper.springdatajpaquerydsl.student.domain.Student;
import com.cooper.springdatajpaquerydsl.student.domain.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findByTagNames(List<String> tagNames) {
        return studentRepository.findAll();
    }

}
