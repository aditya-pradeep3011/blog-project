package com.project.blog.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoryResponse {

	private UUID id;
	private String name;
	private long postCount;
}
