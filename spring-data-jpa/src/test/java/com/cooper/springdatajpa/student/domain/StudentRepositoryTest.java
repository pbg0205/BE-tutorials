package com.cooper.springdatajpa.student.domain;

import com.cooper.springdatajpa.common.config.QueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByAwardName() {
        //given
        Student student1 = new Student("학생1", "태그1");
        Student student2 = new Student("학생2", "태그2");

        Award award1 = Award.of("award1");
        Award award2 = Award.of("award2");
        Award award3 = Award.of("award3");
        Award award4 = Award.of("award4");

        student1.addAward(award1);
        student1.addAward(award2);
        student1.addAward(award3);

        student2.addAward(award2);
        student2.addAward(award3);
        student2.addAward(award4);

        testEntityManager.persist(student1);
        testEntityManager.persist(student2);

        //when
        List<Student> students = studentRepository.findByAwardName(1L);

        //then
        assertThat(students).hasSize(1);
    }

}
