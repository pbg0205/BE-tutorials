package com.cooper.springdatajpaquerydsl.student.domain;


import com.cooper.springdatajpaquerydsl.common.config.QueryDslConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@DisplayName("bulk update 는 영속성 컨텍스트와 관계없이 DB 만 업데이트 한다")
	void bulkUpdateRegardlessPersistenceContext() {
		//given
		Student student1 = testEntityManager.persist(new Student("학생1", "태그1"));
		Student student2 = testEntityManager.persist(new Student("학생2", "태그1"));
		Student student3 = testEntityManager.persist(new Student("학생3", "태그2"));
		Student student4 = testEntityManager.persist(new Student("학생4", "태그2"));

		//when
		studentRepository.updateStudentName("태그1", "update student");

		Student findStudent1 = jdbcTemplate.queryForObject("select * from student where id = ?", new StudentRowMapper(), student1.getId());
		Student findStudent2 = jdbcTemplate.queryForObject("select * from student where id = ?", new StudentRowMapper(), student2.getId());
		Student findStudent3 = jdbcTemplate.queryForObject("select * from student where id = ?", new StudentRowMapper(), student3.getId());
		Student findStudent4 = jdbcTemplate.queryForObject("select * from student where id = ?", new StudentRowMapper(), student4.getId());

		assert findStudent1.getName().equals("update student");
		assert findStudent2.getName().equals("update student");
		assert findStudent3.getName().equals("학생3");
		assert findStudent4.getName().equals("학생4");

		//then
		Assertions.assertAll(
				() -> assertThat(findStudent1.getName()).isNotEqualTo(student1.getName()),
				() -> assertThat(findStudent2.getName()).isNotEqualTo(student2.getName()),
				() -> assertThat(findStudent3.getName()).isEqualTo(student3.getName()),
				() -> assertThat(findStudent4.getName()).isEqualTo(student4.getName())
		);

	}

	@Test
	@DisplayName("학생 별 수상 내용을 조회한다")
	void findAllByStudentId() {
		Student student1 = testEntityManager.persist(new Student("학생1", "태그1"));
		Award award1 = testEntityManager.persist(new Award("수상1"));
		Award award2 = testEntityManager.persist(new Award("수상2"));

		student1.addAward(award1);
		student1.addAward(award2);

		Student student2 = testEntityManager.persist(new Student("학생2", "태그1"));
		Award award3 = testEntityManager.persist(new Award("수상3"));
		Award award4 = testEntityManager.persist(new Award("수상4"));

		student2.addAward(award3);
		student2.addAward(award4);

		List<Student> students = studentRepository.findAllByStudentId(null);
		assertThat(students).hasSize(2);
	}

	@Test
	@DisplayName("pessimistic lock(shared lock) 을 통해 조회한다")
	void findAllByStudentIdWithPessimisticLockRead() {
		//given
		Student savedStudent = testEntityManager.persist(new Student("학생1", "태그1"));

		//when
		Student student = studentRepository.findAllByStudentIdWithPessimisticLockRead(savedStudent.getId());

		//then
		assertThat(student).extracting("id", "name")
				.contains(1L, "학생1");
	}

	@Test
	@DisplayName("pessimistic lock(exclusive lock) 을 통해 조회한다")
	void findAllByStudentIdWithPessimisticLockWrite() {
		//given
		Student savedStudent = testEntityManager.persist(new Student("학생1", "태그1"));

		//when
		Student student = studentRepository.findAllByStudentIdWithPessimisticLockWrite(savedStudent.getId());

		//then
		assertThat(student).extracting("id", "name")
				.contains(1L, "학생1");
	}

	private class StudentRowMapper implements RowMapper<Student> {

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Student(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getString("tag_name")
			);
		}

	}

}
