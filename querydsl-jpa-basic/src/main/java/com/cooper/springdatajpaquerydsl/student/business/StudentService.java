package com.cooper.springdatajpaquerydsl.student.business;

import com.cooper.springdatajpaquerydsl.student.domain.Award;
import com.cooper.springdatajpaquerydsl.student.domain.AwardRepository;
import com.cooper.springdatajpaquerydsl.student.domain.Student;
import com.cooper.springdatajpaquerydsl.student.domain.StudentRepository;
import com.cooper.springdatajpaquerydsl.student.dto.AwardAssignRequest;
import com.cooper.springdatajpaquerydsl.student.dto.AwardCreateRequest;
import com.cooper.springdatajpaquerydsl.student.dto.AwardCreateResponse;
import com.cooper.springdatajpaquerydsl.student.dto.StudentCreateResponse;
import com.cooper.springdatajpaquerydsl.student.dto.StudentCreateRequest;
import com.cooper.springdatajpaquerydsl.student.dto.StudentLookupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AwardRepository awardRepository;

    public List<Student> findByTagNames(List<String> tagNames) {
        return studentRepository.findAll();
    }

    public List<StudentLookupResponse> findByStudentIds(List<Long> studentIds) {
        return studentRepository.findAllByStudentIds(studentIds);
    }

    public AwardCreateResponse createAward(AwardCreateRequest awardCreateRequest) {
        Award award = new Award(awardCreateRequest.getName() + "_" + UUID.randomUUID().toString().substring(0, 5));
        Award savedAward = awardRepository.save(award);
        return new AwardCreateResponse(savedAward.getId(), savedAward.getName());
    }

    @Transactional
    public void assignAward(AwardAssignRequest awardAssignRequest) {
        Student student = studentRepository.findById(awardAssignRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException());
        Award award = awardRepository.findById(awardAssignRequest.getAwardId())
                .orElseThrow(() -> new RuntimeException());

        student.addAward(award);
        System.out.println("student = " + student);
    }

    public StudentCreateResponse createStudent(StudentCreateRequest studentCreateRequest) {
        Student savedStudent = studentRepository.save(new Student(
                studentCreateRequest.getName() + "_" + UUID.randomUUID().toString().substring(0, 5),
                studentCreateRequest.getTagName() + "_" + UUID.randomUUID().toString().substring(0, 5)));

        return new StudentCreateResponse(savedStudent.getId(), savedStudent.getName(), savedStudent.getTagName());
    }
}
