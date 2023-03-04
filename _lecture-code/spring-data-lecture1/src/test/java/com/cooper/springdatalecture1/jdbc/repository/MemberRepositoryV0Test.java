package com.cooper.springdatalecture1.jdbc.repository;

import com.cooper.springdatalecture1.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MemberRepositoryV0Test {

    private MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    /**
     * <pre>
     *     이미 데이터가 생성되어 있다면 java.sql.SQLIntegrityConstraintViolationException 반환한다.
     * </pre>
     */
    @Test
    void crud() throws SQLException {
        //create
        log.info("1. create member start");
        Member member = new Member("member01", 1000);
        Member saveMember = memberRepositoryV0.save(member);
        log.info("--- create member end");

        //select
        log.info("2. select member start");
        Member findMember = memberRepositoryV0.findById("member01");
        assertThat(findMember.getMoney()).isEqualTo(1000);
        log.info("--- select member end");

        //update
        log.info("3. update member start");
        memberRepositoryV0.update("member01", 20000);
        Member updatedMember = memberRepositoryV0.findById("member01");
        assertThat(updatedMember.getMoney()).isEqualTo(20000);
        log.info("--- update member end");

        //delete
        log.info("4. delete member start");
        int deletedCount = memberRepositoryV0.delete("member01");
        assertThat(deletedCount).isEqualTo(1);
        log.info("--- delete member end");
    }

}
