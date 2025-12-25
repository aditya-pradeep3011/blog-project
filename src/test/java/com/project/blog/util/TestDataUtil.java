package com.project.blog.util;

import com.project.blog.domain.Category;
import com.project.blog.domain.Tag;
import com.project.blog.dto.CategoryRequest;

public class TestDataUtil {

	public static Category createTestCategoryA()
	{
		Category category = Category.builder()
									.name("TestCategoryA")
									.build();
		
		return category;
	}
	
	public static CategoryRequest createTestCategoryRequestA()
	{
		CategoryRequest categoryRequest = CategoryRequest.builder()
														 .name("TestCategoryA")
														 .build();
		
		return categoryRequest;
	}
	
	public static Tag createTestTagA()
	{
		Tag tag = Tag.builder()
					 .name("TestTagA")
					 .build();
		
		return tag;
	}
}
