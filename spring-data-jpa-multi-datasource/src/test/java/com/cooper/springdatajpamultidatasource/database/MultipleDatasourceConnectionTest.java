package com.cooper.springdatajpamultidatasource.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MultipleDatasourceConnectionTest {

    @Autowired
    @Qualifier("studentDataSource")
    private DataSource studentDataSource;

    @Autowired
    @Qualifier("teacherDataSource")
    private DataSource teacherDataSource;

    @Test
    void connectSuccessWithStudentDatabase() throws SQLException {
        try (Connection studentConnection = studentDataSource.getConnection()) {
            assertThat(studentConnection).isNotNull();
        }
    }

    @Test
    void connectSuccessWithTeacherDatabase() throws SQLException {
        try (Connection teacherConnection = teacherDataSource.getConnection()) {
            assertThat(teacherConnection).isNotNull();
        }
    }

}
