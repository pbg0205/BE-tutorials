package com.cooper.springdatajpa.student.persistence;

import com.cooper.springdatajpa.student.domain.Student;
import com.cooper.springdatajpa.student.domain.StudentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cooper.springdatajpa.student.domain.QStudent.student;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Student> findByTagNames(List<String> tagNames) {
        return queryFactory.selectFrom(student)
                .where(student.tagName.in(tagNames))
                .fetch();
    }

}
