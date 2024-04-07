package com.cooper.springrestswagger.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import com.cooper.springrestswagger.post.domain.Post;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
public class PostCreateRequest {

	private final String title;
	private final String content;

	public Post toEntity(final Long id) {
		return new Post(id, title, content);
	}

}
