package com.cooper.springrestswagger.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateResponse {

	private final Long postId;

	public static PostCreateResponse create(final Long savedPostId) {
		return new PostCreateResponse(savedPostId);
	}

}
