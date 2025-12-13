package com.project.blog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.domain.Post;
import com.project.blog.dto.PostDto;
import com.project.blog.mapper.PostMapper;
import com.project.blog.service.PostService;

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
}
