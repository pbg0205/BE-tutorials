package com.cooper.springdatajpaquerydslpagination.employees;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cooper.springdatajpaquerydslpagination.employees.QEmployees.employees;

@Repository
@RequiredArgsConstructor
public class EmployeesRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Employees findById(final Long employeeId) {
        return jpaQueryFactory.select(employees)
                .from(employees)
                .where(employees.id.eq(employeeId))
                .fetchOne();
    }

    public List<Employees> findOffsetBasedPagination(final int limit, final Long offset) {
        return jpaQueryFactory.select(employees)
                .from(employees)
                .limit(limit)
                .offset(offset)
                .fetch();
    }

    public List<Employees> findCursorBasedPagination(final int limit, final Long offset) {
        return jpaQueryFactory.select(employees)
                .from(employees)
                .where(employees.id.goe(offset))
                .limit(limit)
                .fetch();
    }

}
