package com.cooper.springdatajpaquerydsl.student.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.cooper.springdatajpaquerydsl.student.domain.QAward.award;
import static com.cooper.springdatajpaquerydsl.student.domain.QStudent.student;

@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 연관관계 조인을 선언할 경우, 중복을 제거해야 한다.
     */
    @Override
    public List<Student> findAllByStudentId(final Long studentId) {
        return jpaQueryFactory.select(student).distinct()
                .from(student)
                .join(student.awards, award)
                .where(eqStudentId(studentId))
                .fetch();
    }

    private BooleanExpression eqStudentId(Long studentId) {
        return (studentId == null) ? null : student.id.eq(studentId);
    }

    @Override
    public void updateStudentName(String tagName, String updateName) {
        jpaQueryFactory.update(student)
                .set(student.name, updateName)
                .where(student.tagName.eq(tagName))
                .execute();
    }

}
