package com.cooper.springdatajpaquerydsl.student.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.cooper.springdatajpaquerydsl.student.domain.QAward.award;
import static com.cooper.springdatajpaquerydsl.student.domain.QStudent.student;

@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Student> findByAwardName(final Long studentId) {
        return jpaQueryFactory.select(student)
                .from(student)
                .join(student.awards, award)
                .where(student.id.eq(studentId))
                .fetch();
    }

    @Override
    public void updateStudentName(String tagName, String updateName) {
        jpaQueryFactory.update(student)
                .set(student.name, updateName)
                .where(student.tagName.eq(tagName))
                .execute();
    }

}
