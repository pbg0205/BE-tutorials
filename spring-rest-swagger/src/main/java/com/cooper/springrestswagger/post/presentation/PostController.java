package com.cooper.springrestswagger.post.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.cooper.springrestswagger.post.business.PostService;
import com.cooper.springrestswagger.post.dto.request.PostCreateRequest;
import com.cooper.springrestswagger.post.dto.request.PostDeleteRequest;
import com.cooper.springrestswagger.post.dto.request.PostUpdateRequest;
import com.cooper.springrestswagger.post.dto.response.PostCreateResponse;
import com.cooper.springrestswagger.post.dto.response.PostDeleteResponse;
import com.cooper.springrestswagger.post.dto.response.PostLookupResponse;
import com.cooper.springrestswagger.post.dto.response.PostUpdateResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<PostCreateResponse> create(@RequestBody final PostCreateRequest postCreateRequest) {
		Long savedPostId = postService.save(postCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(PostCreateResponse.create(savedPostId));
	}

	@GetMapping
	public ResponseEntity<PostLookupResponse> findById(@RequestParam final Long id) {
		PostLookupResponse postLookupResponse = postService.findById(id);
		return ResponseEntity.ok(postLookupResponse);
	}

	@PutMapping
	public ResponseEntity<PostUpdateResponse> update(@RequestBody final PostUpdateRequest postUpdateRequest) {
		PostUpdateResponse postUpdateResponse = postService.update(postUpdateRequest);
		return ResponseEntity.ok(postUpdateResponse);
	}

	@DeleteMapping
	public ResponseEntity<PostDeleteResponse> update(@RequestBody final PostDeleteRequest postDeleteRequest) {
		PostDeleteResponse postDeleteResponse = postService.delete(postDeleteRequest.getId());
		return ResponseEntity.ok(postDeleteResponse);
	}

}
