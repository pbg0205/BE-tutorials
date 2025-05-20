package com.cooper.presigned_url.presentation;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.cooper.presigned_url.service.PostService;
import com.cooper.presigned_url.service.dto.request.PostCreateRequest;
import com.cooper.presigned_url.service.dto.response.PostCreateResponse;
import com.cooper.presigned_url.service.dto.response.PostGetResponse;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/api/posts/presigned")
	public PostCreateResponse createPostByPresignedUrl(@RequestBody final PostCreateRequest postCreateRequest) {
		return postService.createPostByPresigned(postCreateRequest);
	}

	@GetMapping("/api/posts/presigned/{postId}")
	public PostGetResponse getPost(@PathVariable(name = "postId") final Long postId) {
		return postService.getPostByPresigned(postId);
	}

	@PostMapping("/api/posts/multipart")
	public PostCreateResponse createPostByMultipart(
		@RequestPart(name="postCreateRequest") final PostCreateRequest postCreateRequest,
		@RequestPart(name = "uploadFile") final MultipartFile uploadFile) throws IOException {
		return postService.createPostByMultipart(postCreateRequest, uploadFile);
	}
}
