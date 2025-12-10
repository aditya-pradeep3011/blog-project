package com.project.blog.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TagResponse {

	private UUID id;
	private String name;
	private long postCount;
	
}
