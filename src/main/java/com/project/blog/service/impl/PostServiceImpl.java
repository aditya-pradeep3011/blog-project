package com.project.blog.service.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.blog.domain.Category;
import com.project.blog.domain.Post;
import com.project.blog.domain.PostStatus;
import com.project.blog.domain.Tag;
import com.project.blog.domain.User;
import com.project.blog.dto.CreatePostRequest;
import com.project.blog.dto.UpdatePostRequest;
import com.project.blog.repo.PostRepository;
import com.project.blog.service.CategoryService;
import com.project.blog.service.PostService;
import com.project.blog.service.TagService;
import com.project.blog.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final CategoryService categoryService;
	private final TagService tagService;
	private final UserService userService;
	
	private static final int WORDS_PER_MINUTE = 200;

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

	@Override
	@Transactional
	public Post createPost(UUID userId, CreatePostRequest createPostRequest) {
		User loggedInUser = userService.getUserById(userId);
		Post newPost = new Post();
		newPost.setTitle(createPostRequest.getTitle());
		newPost.setContent(createPostRequest.getContent());
		newPost.setAuthor(loggedInUser);
		newPost.setPostStatus(createPostRequest.getPostStatus());
		
		Category category = categoryService.findById(createPostRequest.getCategoryId());
		newPost.setCategory(category);
		
		Set<Tag> tags = tagService.findAllByIds(createPostRequest.getTagIds());
		newPost.setTags(tags);
		
		newPost.setReadTime(calculateReadingTime(createPostRequest.getContent()));
		
		return postRepository.save(newPost);
	}
	
	private Integer calculateReadingTime(String content)
	{
		if(content == null || content.isEmpty())
			return 0;
		
		int wordCount = content.trim().split("\\s+").length;
		
		return (int) Math.ceil((double)wordCount / WORDS_PER_MINUTE);
	}

	@Override
	@Transactional
	public Post updatePost(UUID postId, UpdatePostRequest updatePostRequest) {
		Post existingPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post with ID: "+postId+" could not be found!"));
		existingPost.setTitle(updatePostRequest.getTitle());
		existingPost.setContent(updatePostRequest.getContent());
		existingPost.setPostStatus(updatePostRequest.getPostStatus());
		existingPost.setReadTime(calculateReadingTime(updatePostRequest.getContent()));
		
		if(!existingPost.getCategory().getId().equals(updatePostRequest.getCategoryId()))
		{
			Category category = categoryService.findById(updatePostRequest.getCategoryId());
			existingPost.setCategory(category);
		}
		
		Set<UUID> existingPostTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
		if(!updatePostRequest.getTagIds().equals(existingPostTagIds))
		{
			Set<Tag> tags = tagService.findAllByIds(updatePostRequest.getTagIds());
			existingPost.setTags(tags);
		}
		
		return postRepository.save(existingPost);
	}

}
