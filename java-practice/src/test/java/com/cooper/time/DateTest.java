package com.cooper.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DateTest {

    @Test
    @DisplayName("X.before(Y) : X 가 Y보다 이전 날짜인지 확인 (X < Y == true)")
    void before() {
        Date beforeDate = new Date(System.currentTimeMillis() - 1);
        Date nowDate = new Date();
        Date laterDate = new Date(System.currentTimeMillis() + 1);

        assertAll(
                () -> assertThat(nowDate.before(laterDate)).isTrue(),
                () -> assertThat(nowDate.before(nowDate)).isFalse(),
                () -> assertThat(nowDate.before(beforeDate)).isFalse()
        );

    }

    @Test
    @DisplayName("X.after(Y) : X 가 Y보다 이후 날짜인지 확인 (X > Y == true)")
    void after() {
        Date beforeDate = new Date(System.currentTimeMillis() - 1);
        Date nowDate = new Date();
        Date laterDate = new Date(System.currentTimeMillis() + 1);


        assertAll(
                () -> assertThat(nowDate.after(beforeDate)).isTrue(),
                () -> assertThat(nowDate.after(nowDate)).isFalse(),
                () -> assertThat(nowDate.after(laterDate)).isFalse()
        );

    }

}
