package com.cooper.springdatalecture1.jdbc.connection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionConstants {

    public static final String URL = "jdbc:mysql://localhost:3306/cooper_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "cooper2021";

}
