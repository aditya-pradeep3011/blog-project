package com.project.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

	@NotBlank(message = "Category name must not be empty!")
	@Size(min = 2, max = 15, message = "Category name must be between {min} and {max} characters!")
	@Pattern(regexp = "^[\\w\\s-]+$", message = "Category name must only contain alphabets, numbers, spaces, underscores or hyphens!")
	private String name;
}
