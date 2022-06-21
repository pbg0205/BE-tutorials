package com.cooper.basic;

import com.cooper.basic.domain.Member;
import com.cooper.basic.domain.Team;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.cooper.basic.domain.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class FetchTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        for (int teamNum = 1; teamNum <= 10; teamNum++) {
            String teamName = "team" + teamNum;
            Team team = Team.create(teamName);
            entityManager.persist(team);
            addMemberSample(team, teamNum);
        }
    }

    private void addMemberSample(Team team, int teamNum) {
        int startNum = ((teamNum - 1) * 100) + 1;
        int endNum = teamNum * 100;
        for (int memberNum = startNum; memberNum <= endNum; memberNum++) {
            String memberName = "member" + memberNum;
            Member member = Member.create(memberName, memberNum, team);
            entityManager.persist(member);
        }
    }

    @Test
    void fetch() {
        List<Member> members = queryFactory.selectFrom(member).fetch();
        assertThat(members).hasSize(1000);
    }

    @Test
    void fetchOne() {
        Member findMember = queryFactory.selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void fetchOneWhenDuplicate() {
        assertThatThrownBy(() -> queryFactory.selectFrom(member).fetchOne())
                .isInstanceOf(NonUniqueResultException.class);
    }

    @Test
    void fetchFirst() {
        Member findMember = queryFactory.selectFrom(member).fetchFirst();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void fetchResults() {
        /*
         * fetchResults() : 페이징 정보 포함, total count 쿼리 추가 실행 (deprecated)
         */
        QueryResults<Member> memberQueryResults = queryFactory.selectFrom(member).fetchResults();
        assertAll(
                () -> assertThat(memberQueryResults.getResults()).hasSize(1000),
                () -> assertThat(memberQueryResults.getLimit()).isEqualTo(Long.MAX_VALUE),
                () -> assertThat(memberQueryResults.getOffset()).isEqualTo(0L),
                () -> assertThat(memberQueryResults.getTotal()).isEqualTo(1000)
        );
    }

}
