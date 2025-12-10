package com.project.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.domain.Tag;
import com.project.blog.dto.TagRequest;
import com.project.blog.dto.TagResponse;
import com.project.blog.mapper.TagMapper;
import com.project.blog.service.TagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
public class TagController {

	private final TagService tagService;
	private final TagMapper tagMapper;
	
	@GetMapping
	public ResponseEntity<List<TagResponse>> getAllTags()
	{
		List<TagResponse> responses = tagService.getAllTags().stream().map(tagMapper::toDto).toList();
		return new ResponseEntity<List<TagResponse>>(responses, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<List<TagResponse>> createTag(@RequestBody @Valid TagRequest tagRequest)
	{
		List<Tag> createdTags = tagService.createTags(tagRequest.getNames());
		
		List<TagResponse> responses = createdTags.stream()
												 .map(tagMapper::toDto)
												 .toList();
		
		return new ResponseEntity<List<TagResponse>>(responses, HttpStatus.CREATED);
		
	}
}
