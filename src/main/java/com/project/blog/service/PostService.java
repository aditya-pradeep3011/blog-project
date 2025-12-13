package com.project.blog.service;

import java.util.List;
import java.util.UUID;

import com.project.blog.domain.Post;

public interface PostService {

	List<Post> getAllPosts(UUID categoryId, UUID tagId);
	
}
