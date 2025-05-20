package com.cooper.presigned_url.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.cooper.presigned_url.service.PostRepository;
import com.cooper.presigned_url.service.model.Post;

@Repository
@RequiredArgsConstructor
public class InMemoryPostRepository implements PostRepository {

	private final Map<Long, Post> db = new HashMap<>();

	@Override
	public Optional<Post> findById(final Long id) {
		final Post post = db.getOrDefault(id, null);
		return Optional.ofNullable(post);
	}

	@Override
	public Post save(final Post post) {
		db.put(post.getId(), post);
		return db.get(post.getId());
	}
}
