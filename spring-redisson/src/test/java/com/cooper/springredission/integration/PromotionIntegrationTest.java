package com.cooper.springredission.integration;

import com.cooper.springredission.domain.Promotion;
import com.cooper.springredission.domain.PromotionRepository;
import com.cooper.springredission.dto.PromotionCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PromotionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PromotionRepository promotionRepository;

    @Test
    @DisplayName("프로모션 생성 api 테스트")
    void createPromotion() throws Exception {
        //given
        PromotionCreateRequest promotionCreateRequest = new PromotionCreateRequest("2022 cooper concert", 100L);
        String requestBody = objectMapper.writeValueAsString(promotionCreateRequest);

        //when, then
        mockMvc.perform(post("/api/v1/promotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.promotionName").value("2022 cooper concert"),
                        jsonPath("$.ticketMaxLimit").value("100"))
                .andDo(print());
    }

    @Test
    @DisplayName("티켓 생성 api 테스트")
    void createTicket() throws Exception {
        //given
        Promotion promotion = promotionRepository.save(new Promotion("2022 cooper concert", 100L));

        //when
        mockMvc.perform(post("/api/v1/promotions/{promotionId}/ticket", promotion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.promotionName").value("2022 cooper concert"),
                        jsonPath("$.ticketSize").value("1"))
                .andDo(print());
    }

}
