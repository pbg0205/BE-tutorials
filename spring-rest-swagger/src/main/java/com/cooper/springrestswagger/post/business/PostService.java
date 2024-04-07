package com.cooper.springrestswagger.post.business;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.cooper.springrestswagger.post.domain.Post;
import com.cooper.springrestswagger.post.dto.request.PostCreateRequest;
import com.cooper.springrestswagger.post.dto.request.PostUpdateRequest;
import com.cooper.springrestswagger.post.dto.response.PostDeleteResponse;
import com.cooper.springrestswagger.post.dto.response.PostLookupResponse;
import com.cooper.springrestswagger.post.dto.response.PostUpdateResponse;
import com.cooper.springrestswagger.post.persistence.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final AtomicLong idGenerator = new AtomicLong();

	public Long save(final PostCreateRequest postCreateRequest) {
		Post post = postCreateRequest.toEntity(idGenerator.incrementAndGet());
		return postRepository.save(post);
	}

	public PostLookupResponse findById(final Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("not found post"));

		return new PostLookupResponse(post.getId(), post.getTitle(), post.getContent());
	}

	public PostUpdateResponse update(final PostUpdateRequest postUpdateRequest) {
		Post updatedPost = postRepository.update(postUpdateRequest);
		return new PostUpdateResponse(updatedPost.getId(), updatedPost.getTitle(), updatedPost.getContent());
	}

	public PostDeleteResponse delete(final Long id) {
		Boolean deleted = postRepository.delete(id);
		return new PostDeleteResponse(deleted);
	}

}
