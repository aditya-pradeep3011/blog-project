package com.project.blog.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.blog.domain.Category;
import com.project.blog.domain.Post;
import com.project.blog.domain.PostStatus;
import com.project.blog.domain.Tag;
import com.project.blog.domain.User;
import com.project.blog.repo.PostRepository;
import com.project.blog.service.CategoryService;
import com.project.blog.service.PostService;
import com.project.blog.service.TagService;
import com.project.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final CategoryService categoryService;
	private final TagService tagService;
	private final UserService userService;

	@Override
	public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
		if(categoryId != null && tagId != null)
		{
			Category category = categoryService.findById(categoryId);
			Tag tag = tagService.findById(tagId);
			return postRepository.findAllByPostStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag);
		}
		
		if(categoryId != null)
		{
			Category category = categoryService.findById(categoryId);
			return postRepository.findAllByPostStatusAndCategory(PostStatus.PUBLISHED, category);
		}
		
		if(tagId != null)
		{
			Tag tag = tagService.findById(tagId);
			return postRepository.findAllByPostStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
		}
		
		return postRepository.findAllByPostStatus(PostStatus.PUBLISHED);
	}

	@Override
	public List<Post> getAllDraftPosts(UUID userId) {
		User loggedInUser = userService.getUserById(userId);
		return postRepository.findAllByPostStatusAndAuthor(PostStatus.DRAFT, loggedInUser);
	}

}
