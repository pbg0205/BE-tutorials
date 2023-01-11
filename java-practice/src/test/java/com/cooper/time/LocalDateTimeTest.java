package com.cooper.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeTest {

    @Test
    @DisplayName("LocalDateTime 에서 Date 타입으로 변환")
    void localDateTimeToDate() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        Date nowDate = Timestamp.valueOf(nowDateTime);

        assertThat(nowDate).isInstanceOf(Date.class);
    }

}
