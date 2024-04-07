package com.cooper.springrestswagger.post.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.cooper.springrestswagger.post.domain.Post;
import com.cooper.springrestswagger.post.dto.request.PostUpdateRequest;

@Repository
public class PostRepository {

	private final Map<Long, Post> postHashMap = new ConcurrentHashMap<>();

	public Long save(Post post) {
		if (postHashMap.containsKey(post.getId())) {
			throw new IllegalArgumentException("invalid id");
		}

		postHashMap.put(post.getId(), post);

		return postHashMap.get(post.getId()).getId();
	}

	public Optional<Post> findById(final Long postId) {
		return Optional.ofNullable(postHashMap.get(postId));
	}

	public Post update(final PostUpdateRequest postUpdateRequest) {
		Long id = postUpdateRequest.getId();

		if (!postHashMap.containsKey(id)) {
			throw new IllegalArgumentException("not found post");
		}

		Post post = postHashMap.get(id);
		return post.update(id, postUpdateRequest.getTitle(), postUpdateRequest.getContent());
	}

	public Boolean delete(final Long id) {
		if (!postHashMap.containsKey(id)) {
			throw new IllegalArgumentException("not found post");
		}

		return postHashMap.remove(id) != null;
	}

}
