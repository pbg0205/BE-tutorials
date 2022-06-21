package com.cooper.basic;

import com.cooper.basic.domain.Member;
import com.cooper.basic.domain.Team;
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
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class PagingTest {

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
    void page() {
        List<Member> members = queryFactory.selectFrom(member)
                .orderBy(member.username.desc())
                .offset(0)
                .limit(10)
                .fetch();

        assertAll(
                () -> assertThat(members).hasSize(10),
                () -> assertThat(members).extracting(Member::getUsername)
                        .usingRecursiveFieldByFieldElementComparator().contains("member999")
        );
    }

}
