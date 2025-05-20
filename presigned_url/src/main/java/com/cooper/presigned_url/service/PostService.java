package com.cooper.presigned_url.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.cooper.presigned_url.service.dto.request.PostCreateRequest;
import com.cooper.presigned_url.service.dto.response.PostCreateResponse;
import com.cooper.presigned_url.service.dto.response.PostGetResponse;
import com.cooper.presigned_url.service.model.Post;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final FileHandler fileHandler;

	public PostCreateResponse createPostByPresigned(final PostCreateRequest postCreateRequest) {
		final Long postId = postCreateRequest.id();

		final URL signedUrl = fileHandler.createSignedUrl(postId, postCreateRequest.fileName());
		final Post post = new Post(postId, postCreateRequest.title(), postCreateRequest.content(), signedUrl.getPath());

		final Post savedPost = postRepository.save(post);

		return new PostCreateResponse(savedPost.getId(), signedUrl.toString());
	}

	public PostGetResponse getPostByPresigned(final Long postId) {
		final Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("Post not found"));

		final URL signedUrl = fileHandler.getSignedUrl(postId, post.getPath());

		return new PostGetResponse(post.getId(), post.getTitle(), post.getContent(), signedUrl.toString());
	}

	public PostCreateResponse createPostByMultipart(final PostCreateRequest postCreateRequest,
		final MultipartFile file) throws IOException {
		final Long id = postCreateRequest.id();

		final URL uploadUrl = fileHandler.uploadFile(id, file);
		final Post post = new Post(postCreateRequest.id(), postCreateRequest.title(), postCreateRequest.content(),
			uploadUrl.getPath());

		final Post savedPost = postRepository.save(post);

		return new PostCreateResponse(savedPost.getId(), uploadUrl.toString());
	}
}
