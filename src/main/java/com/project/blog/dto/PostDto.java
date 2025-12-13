package com.project.blog.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.project.blog.domain.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

	private UUID id;
	private String title;
	private String content;
	private Integer readTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private PostStatus postStatus;
	private CategoryResponse category;
	private Set<TagResponse> tags;
	private AuthorDto author;
}
