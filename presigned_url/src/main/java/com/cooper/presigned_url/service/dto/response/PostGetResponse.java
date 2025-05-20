package com.cooper.presigned_url.service.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostGetResponse {
	private final Long id;
	private final String title;
	private final String content;
	private final String path;
}
