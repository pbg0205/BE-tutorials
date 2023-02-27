package com.cooper.springdatajpa.student.domain;

import java.util.List;

public interface StudentRepositoryCustom {

    List<Student> findByAwardName(final Long studentId);

    void updateStudentName(String tagName, String updateName);

}
