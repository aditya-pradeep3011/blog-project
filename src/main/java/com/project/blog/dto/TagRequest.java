package com.project.blog.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagRequest {

	@NotEmpty(message = "At least one tag name is required!")
	private Set<
				@NotBlank(message = "Tag name must not be empty!")
				@Size(min = 2, max = 15, message = "Tag name must be between {min} and {max} characters!")
				@Pattern(regexp = "^[\\w]+$", message = "Tag name must only contain alphabets, numbers and underscores!")
				String> names;
}
