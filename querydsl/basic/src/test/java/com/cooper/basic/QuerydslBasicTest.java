package com.cooper.basic;

import com.cooper.basic.domain.Member;
import com.cooper.basic.domain.QMember;
import com.cooper.basic.domain.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Team teamA = Team.create("team01");
        Team teamB = Team.create("team02");

        entityManager.persist(teamA);
        entityManager.persist(teamB);

        Member member1 = Member.create("member1", 10, teamA);
        Member member2 = Member.create("member2", 20, teamA);
        Member member3 = Member.create("member3", 30, teamB);
        Member member4 = Member.create("member4", 40, teamB);

        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);
    }

    @DisplayName("JPQL 테스트")
    @Test
    void jpqlTest() {
        String jpql = "select m from Member m where m.username = :username";

        Member member = entityManager.createQuery(jpql, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(member.getUsername()).isEqualTo("member1");
    }

    @DisplayName("QueryDSL 테스트")
    @Test
    void queryDslTest() {
        Member member = jpaQueryFactory.select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        assertThat(member.getUsername()).isEqualTo("member1");
    }

}
