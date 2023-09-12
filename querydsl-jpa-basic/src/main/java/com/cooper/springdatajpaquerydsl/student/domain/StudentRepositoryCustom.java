package com.cooper.springdatajpaquerydsl.student.domain;

import com.cooper.springdatajpaquerydsl.student.dto.StudentLookupResponse;

import java.util.List;

public interface StudentRepositoryCustom {

    List<Student> findAllByStudentId(final Long studentId);

    void updateStudentName(String tagName, String updateName);

    List<StudentLookupResponse> findAllByStudentIds(List<Long> studentIds);
}
