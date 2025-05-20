package com.cooper.presigned_url.service;

import java.util.Optional;

import com.cooper.presigned_url.service.model.Post;

public interface PostRepository {
	Optional<Post> findById(Long id);
	Post save(Post post);
}
