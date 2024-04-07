package com.cooper.springrestswagger.post.domain;

import lombok.Getter;

@Getter
public class Post {

	private final Long id;
	private final String title;
	private final String content;

	public Post(final Long id, final String title, final String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Post update(final Long id, final String title, final String content) {
		return new Post(id, title, content);
	}

}
