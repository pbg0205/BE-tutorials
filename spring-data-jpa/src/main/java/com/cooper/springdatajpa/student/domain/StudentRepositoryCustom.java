package com.cooper.springdatajpa.student.domain;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> findByTagNames(List<String> tagNames);
}
