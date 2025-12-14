package com.project.blog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.domain.Post;
import com.project.blog.dto.CreatePostRequest;
import com.project.blog.dto.CreatePostRequestDto;
import com.project.blog.dto.PostDto;
import com.project.blog.dto.UpdatePostRequestDto;
import com.project.blog.mapper.PostMapper;
import com.project.blog.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

	private final PostService postService;
	private final PostMapper postMapper;
	
	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(required = false) UUID categoryId, @RequestParam(required = false) UUID tagId)
	{
		List<Post> posts = postService.getAllPosts(categoryId, tagId);
		List<PostDto> response = posts.stream().map(postMapper::toDto).toList();
		return new ResponseEntity<List<PostDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/draft")
	public ResponseEntity<List<PostDto>> getAllDraftPosts(@RequestAttribute UUID username)
	{
		List<Post> posts = postService.getAllDraftPosts(username);
		List<PostDto> response = posts.stream().map(postMapper::toDto).toList();
		return new ResponseEntity<List<PostDto>>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto, @RequestAttribute UUID username)
	{
		CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
	 	Post createdPost = postService.createPost(username, createPostRequest);
	 	PostDto response = postMapper.toDto(createdPost);
	 	return new ResponseEntity<PostDto>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody UpdatePostRequestDto updatePostRequestDto, @PathVariable UUID postId)
	{
		Post post = postService.updatePost(postId, postMapper.toUpdatePostRequest(updatePostRequestDto));
		PostDto response = postMapper.toDto(post);
		return new ResponseEntity<PostDto>(response, HttpStatus.OK);
	}
}
