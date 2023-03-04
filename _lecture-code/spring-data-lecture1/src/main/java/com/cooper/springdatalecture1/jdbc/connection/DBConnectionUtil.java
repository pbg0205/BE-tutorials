package com.cooper.springdatalecture1.jdbc.connection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.cooper.springdatalecture1.jdbc.connection.ConnectionConstants.PASSWORD;
import static com.cooper.springdatalecture1.jdbc.connection.ConnectionConstants.URL;
import static com.cooper.springdatalecture1.jdbc.connection.ConnectionConstants.USERNAME;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DBConnectionUtil {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("connection info: {}, connection class = {}", connection, connection.getClass());
            return connection;
        } catch (SQLException sqlException) {
            throw new IllegalStateException(sqlException);
        }

    }

}
