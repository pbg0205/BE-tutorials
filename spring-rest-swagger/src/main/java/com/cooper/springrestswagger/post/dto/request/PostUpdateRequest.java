package com.cooper.springrestswagger.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PostUpdateRequest {
	private final Long id;
	private final String title;
	private final String content;
}
