package com.cooper.springrestswagger.post.documentation;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import com.cooper.springrestswagger.post.business.PostService;
import com.cooper.springrestswagger.post.dto.request.PostCreateRequest;
import com.cooper.springrestswagger.post.dto.request.PostDeleteRequest;
import com.cooper.springrestswagger.post.dto.request.PostUpdateRequest;
import com.cooper.springrestswagger.post.dto.response.PostDeleteResponse;
import com.cooper.springrestswagger.post.dto.response.PostLookupResponse;
import com.cooper.springrestswagger.post.dto.response.PostUpdateResponse;

class PostDocumentation extends Documentation {

	@MockBean
	private PostService postService;

	@Test
	@DisplayName("게시물 생성")
	void createPost() throws Exception {
		//given
		given(postService.save(any())).willReturn(1L);

		PostCreateRequest postCreateRequest = new PostCreateRequest("title", "content");
		String requestBody = objectMapper.writeValueAsString(postCreateRequest);

		// when, then
		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(document(
				"member-create",
				memberCreateRequest(),
				memberResponse()));
	}

	private Snippet memberCreateRequest() {
		return requestFields(
			fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 이름"),
			fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
		);
	}

	private Snippet memberResponse() {
		return responseFields(
			fieldWithPath("postId").type(JsonFieldType.NUMBER).description("생성된 게시물 아이디")
		);
	}

	@Test
	@DisplayName("게시물 조회 성공")
	void findByIdSuccess() throws Exception {
		//given
		long postId = 1L;
		given(postService.findById(any())).willReturn(new PostLookupResponse(postId, "title", "content"));

		// when
		mockMvc.perform(get("/api/posts").queryParam("id", String.valueOf(postId))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("member-find-success", memberFindRequest(), memberFindResponse()));
	}

	private Snippet memberFindRequest() {
		return queryParameters(parameterWithName("id").description("게시물 아이디"));
	}

	private Snippet memberFindResponse() {
		return responseFields(
			fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
			fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
			fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
		);
	}

	@Test
	@DisplayName("게시물 조회 실패")
	void findByIdFail() throws Exception {
		//given
		long postId = 2L;
		given(postService.findById(any())).willThrow(new IllegalArgumentException("not found post"));

		// when
		mockMvc.perform(get("/api/posts").queryParam("id", String.valueOf(postId))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andDo(document("member-find-fail", memberFindBadRequest(), memberFindBadResponse()));
	}

	private Snippet memberFindBadRequest() {
		return queryParameters(parameterWithName("id").description("게시물 아이디"));
	}

	private Snippet memberFindBadResponse() {
		return responseFields(
			fieldWithPath("type").type(JsonFieldType.STRING).description("에러 타입"),
			fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지")
		);
	}

	@Test
	@DisplayName("게시물 갱신 성공")
	void update() throws Exception {
		//given
		long postId = 1L;
		String updateTitle = "update title";
		String updateContent = "update content";

		given(postService.update(any())).willReturn(new PostUpdateResponse(postId, updateTitle, updateContent));

		PostUpdateRequest postUpdateRequest = new PostUpdateRequest(postId, updateTitle, updateContent);
		String requestBody = objectMapper.writeValueAsString(postUpdateRequest);

		// when
		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/posts")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(),
				jsonPath("id").value(postId),
				jsonPath("title").value(updateTitle),
				jsonPath("content").value(updateContent))
			.andDo(document("member-update-success", memberUpdateRequest(), memberUpdateResponse()));
	}

	private Snippet memberUpdateRequest() {
		return requestFields(
			fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
			fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 이름"),
			fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
		);
	}

	private Snippet memberUpdateResponse() {
		return responseFields(
			fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
			fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
			fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
		);
	}

	@Test
	@DisplayName("게시물 갱신 실패")
	void updateFail() throws Exception {
		//given
		long postId = 2L;
		String updateTitle = "update title";
		String updateContent = "update content";

		given(postService.update(any())).willThrow(new IllegalArgumentException("not found post"));

		PostUpdateRequest postUpdateRequest = new PostUpdateRequest(postId, updateTitle, updateContent);
		String requestBody = objectMapper.writeValueAsString(postUpdateRequest);

		// when
		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/posts")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isBadRequest())
			.andDo(document("member-update-fail", memberUpdateFailRequest(), memberUpdateFailResponse()));
	}

	private Snippet memberUpdateFailRequest() {
		return requestFields(
			fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
			fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 이름"),
			fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
		);
	}

	private Snippet memberUpdateFailResponse() {
		return responseFields(
			fieldWithPath("type").type(JsonFieldType.STRING).description("에러 타입"),
			fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메세지")
		);
	}

	@Test
	@DisplayName("게시물 삭제")
	void delete() throws Exception {
		//given
		long postId = 1L;

		given(postService.delete(any())).willReturn(new PostDeleteResponse(true));
		PostDeleteRequest postDeleteRequest = new PostDeleteRequest(postId);
		String requestBody = objectMapper.writeValueAsString(postDeleteRequest);

		// when
		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/posts")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(),
				jsonPath("deleted").value(true))
			.andDo(document("member-delete", memberDeleteRequest(), memberDeleteResponse()));
	}

	private Snippet memberDeleteRequest() {
		return requestFields(
			fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디")
		);
	}

	private Snippet memberDeleteResponse() {
		return responseFields(
			fieldWithPath("deleted").type(JsonFieldType.BOOLEAN).description("게시물 삭제여부")
		);
	}

}
