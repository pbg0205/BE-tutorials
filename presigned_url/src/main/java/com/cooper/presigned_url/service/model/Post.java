package com.cooper.presigned_url.service.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Post {

	private Long id;

	private String title;

	private String content;

	private String path;

	private Boolean deleted;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Post(final Long id, final String title, final String content, final String path) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.path = path;
		this.deleted = false;
	}
}
