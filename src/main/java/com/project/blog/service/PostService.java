package com.project.blog.service;

import java.util.List;
import java.util.UUID;

import com.project.blog.domain.Post;
import com.project.blog.dto.CreatePostRequest;
import com.project.blog.dto.UpdatePostRequest;

public interface PostService {

	List<Post> getAllPosts(UUID categoryId, UUID tagId);
	
	List<Post> getAllDraftPosts(UUID userId);
	
	Post createPost(UUID userId, CreatePostRequest createPostRequest);
	
	Post updatePost(UUID postId, UpdatePostRequest updatePostRequest);
	
	Post getPost(UUID postId);
}
