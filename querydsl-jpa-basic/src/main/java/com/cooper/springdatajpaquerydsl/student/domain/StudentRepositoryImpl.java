package com.cooper.springdatajpaquerydsl.student.domain;

import com.cooper.springdatajpaquerydsl.student.dto.AwardLookupResponse;
import com.cooper.springdatajpaquerydsl.student.dto.StudentLookupResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.LockModeType;
import java.util.List;

import static com.cooper.springdatajpaquerydsl.student.domain.QAward.award;
import static com.cooper.springdatajpaquerydsl.student.domain.QStudent.student;
import static com.querydsl.core.group.GroupBy.groupBy;

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

    @Override
    public void updateStudentName(String tagName, String updateName) {
        jpaQueryFactory.update(student)
                .set(student.name, updateName)
                .where(student.tagName.eq(tagName))
                .execute();
    }

    @Override
    public List<StudentLookupResponse> findAllByStudentIds(List<Long> studentIds) {
        return jpaQueryFactory.select(student)
                .from(student)
                .leftJoin(award).on(student.id.eq(award.student.id))
                .where(student.id.in(studentIds))
                .transform(groupBy(student.id)
                        .list(Projections.constructor(StudentLookupResponse.class,
                                student.id,
                                student.name,
                                student.tagName,
                                GroupBy.list(
                                        Projections.constructor(AwardLookupResponse.class,
                                                award.id,
                                                award.name)))));
    }

    @Override
    public Student findAllByStudentIdWithPessimisticLockWrite(Long studentId) {
        return jpaQueryFactory.select(student).distinct()
                .from(student)
                .leftJoin(student.awards, award)
                .where(eqStudentId(studentId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .setHint("jakarta.persistence.lock.timeout", "3000")
                .fetchOne();
    }

    @Override
    public Student findAllByStudentIdWithPessimisticLockRead(Long studentId) {
        return jpaQueryFactory.select(student).distinct()
                .from(student)
                .leftJoin(student.awards, award)
                .where(eqStudentId(studentId))
                .setLockMode(LockModeType.PESSIMISTIC_READ)
                .setHint("jakarta.persistence.lock.timeout", "3000")
                .fetchOne();
    }

    private BooleanExpression eqStudentId(Long studentId) {
        return (studentId == null) ? null : student.id.eq(studentId);
    }

}
