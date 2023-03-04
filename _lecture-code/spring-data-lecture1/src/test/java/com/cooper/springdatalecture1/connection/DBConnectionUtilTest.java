package com.cooper.springdatalecture1.connection;

import com.cooper.springdatalecture1.jdbc.connection.DBConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DBConnectionUtilTest {

    @Test
    void connection() throws SQLException {
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }

}
