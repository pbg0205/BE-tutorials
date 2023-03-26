package com.cooper.springdatajpamultidatasource.student.business;

import com.cooper.springdatajpamultidatasource.student.dto.StudentCreateRequestDto;
import com.cooper.springdatajpamultidatasource.student.dto.StudentUpdateRequestDto;
import com.cooper.springdatajpamultidatasource.student.domain.Student;
import com.cooper.springdatajpamultidatasource.student.domain.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Long save(StudentCreateRequestDto studentCreateRequestDto) {
        Student student = studentRepository.save(new Student(
                studentCreateRequestDto.getName(),
                studentCreateRequestDto.getClassNumber()));

        return student.getId();
    }

    @Transactional
    public Student update(StudentUpdateRequestDto studentUpdateRequestDto) {
        Student student = studentRepository.findById(studentUpdateRequestDto.getId()).orElseThrow(RuntimeException::new);
        student.update(studentUpdateRequestDto.getName(), studentUpdateRequestDto.getClassNumber());
        return student;
    }

    @Transactional(readOnly = true)
    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(RuntimeException::new);
    }

    public void delete(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(RuntimeException::new);
        studentRepository.delete(student);
    }

}
