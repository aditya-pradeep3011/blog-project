package com.project.blog.util;

import com.project.blog.domain.Category;

public class TestDataUtil {

	public static Category createTestCategoryA()
	{
		Category category = Category.builder()
									.name("TestCategoryA")
									.build();
		
		return category;
	}
}
