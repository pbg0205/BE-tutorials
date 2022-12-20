package com.example.springfeignclient.client.presentation;

import com.example.springfeignclient.mock.WireMockClientTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockClientTest
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("feign_client 정상 동작 테스트")
    @Test
    void feign_client_test() throws Exception {
        //given, when
        mockMvc.perform(get("/api/client/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("ServerController response by feign client")
                );
    }

}
