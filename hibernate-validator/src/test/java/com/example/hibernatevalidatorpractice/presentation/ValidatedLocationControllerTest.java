package com.example.hibernatevalidatorpractice.presentation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ValidatedLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("경도, 위도 검증 테스트")
    @Test
    void validateLatitudeAndLongitude() throws Exception {
        //given
        double longitude = -181;
        double latitude = -181;

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/location")
                .param("longitude", String.valueOf(longitude))
                .param("latitude", String.valueOf(latitude)))
                .andDo(print());

        //then
        resultActions.andExpectAll(
                status().isBadRequest());
    }

}
