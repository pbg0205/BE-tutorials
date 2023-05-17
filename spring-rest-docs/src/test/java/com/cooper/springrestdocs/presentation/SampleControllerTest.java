package com.cooper.springrestdocs.presentation;

import com.cooper.springrestdocs.business.SampleService;
import com.cooper.springrestdocs.dto.SampleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@WebMvcTest(controllers = SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleService sampleService;

    @Test
    @DisplayName("샘플 조회")
    void find_sample_by_id() throws Exception {
        //given
        given(sampleService.findSampleById(any())).willReturn(new SampleResponse("sample_id", "sample_name"));

        mockMvc.perform(RestDocumentationRequestBuilders.get(
                "/api/v1/sample/{sampleId}?reqParam1={requestParam1}&reqParam2={requestParam2}",
                                "sample_id",
                                "requestParam1",
                                "requestParam2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("find-sample",
                                findSpecificSamplePathVariables(),
                                findSpecificSampleRequestParams()));
    }

    private Snippet findSpecificSamplePathVariables() {
        return pathParameters(parameterWithName("sampleId").description("샘플 아이디"));
    }

    private Snippet findSpecificSampleRequestParams() {
        return requestParameters(
                parameterWithName("reqParam1").description("리퀘스트 파라미터1"),
                parameterWithName("reqParam2").description("리퀘스트 파라미터2")
        );
    }

}
