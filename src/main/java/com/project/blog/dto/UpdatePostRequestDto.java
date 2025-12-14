package com.project.blog.dto;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

import com.project.blog.domain.PostStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostRequestDto {

	@NotBlank(message = "Title is required")
	@Size(min = 3, max = 20, message = "Title must be between {min} and {max} characters")
	private String title;
	
	@NotBlank(message = "Content is required")
	@Size(min = 10, max = 20000, message = "Content must be between {min} and {max} characters")
	private String content;
	
	@NotNull(message = "Category is required")
	private UUID categoryId;
	
	@Size(max = 10, message = "Maximum of only {max} tags allowed")
	@Builder.Default
	private Set<UUID> tagIds = new HashSet<>();
	
	@NotNull(message = "Post status is required")
	private PostStatus postStatus;
}
