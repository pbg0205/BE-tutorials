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

		Student findStudent1 = jdbcTemplate.queryForObject("select * from student where id = ?", new Object[]{student1.getId()}, new StudentRowMapper());
		Student findStudent2 = jdbcTemplate.queryForObject("select * from student where id = ?", new Object[]{student2.getId()}, new StudentRowMapper());
		Student findStudent3 = jdbcTemplate.queryForObject("select * from student where id = ?", new Object[]{student3.getId()}, new StudentRowMapper());
		Student findStudent4 = jdbcTemplate.queryForObject("select * from student where id = ?", new Object[]{student4.getId()}, new StudentRowMapper());

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
