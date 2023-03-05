package com.cooper.springdatalecture1.jdbc.repository;

import com.cooper.springdatalecture1.jdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.cooper.springdatalecture1.jdbc.connection.ConnectionConstants.PASSWORD;
import static com.cooper.springdatalecture1.jdbc.connection.ConnectionConstants.URL;
import static com.cooper.springdatalecture1.jdbc.connection.ConnectionConstants.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    private MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void setUp() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
    }

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
        Member saveMember = memberRepositoryV1.save(member);
        log.info("--- create member end");

        //select
        log.info("2. select member start");
        Member findMember = memberRepositoryV1.findById("member01");
        assertThat(findMember.getMoney()).isEqualTo(1000);
        log.info("--- select member end");

        //update
        log.info("3. update member start");
        memberRepositoryV1.update("member01", 20000);
        Member updatedMember = memberRepositoryV1.findById("member01");
        assertThat(updatedMember.getMoney()).isEqualTo(20000);
        log.info("--- update member end");

        //delete
        log.info("4. delete member start");
        memberRepositoryV1.delete("member01");
        assertThatThrownBy(() -> memberRepositoryV1.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
        log.info("--- delete member end");
    }

}
