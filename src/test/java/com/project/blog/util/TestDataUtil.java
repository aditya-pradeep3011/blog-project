package com.project.blog.util;

import java.util.HashSet;
import java.util.Set;

import com.project.blog.domain.Category;
import com.project.blog.domain.Post;
import com.project.blog.domain.PostStatus;
import com.project.blog.domain.Tag;
import com.project.blog.domain.User;
import com.project.blog.dto.CategoryRequest;
import com.project.blog.dto.TagRequest;

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
	
	public static TagRequest createTestTagRequestA()
	{
		Set<String> names = new HashSet<>();
		names.add("TestTagA");
		
		TagRequest tagRequest = TagRequest.builder()
										  .names(names)
										  .build();
		
		return tagRequest;
	}
	
	public static User createTestUserA()
	{
		User user = User.builder()
						.email("test@test.com")
						.name("TestUserA")
						.password("testpassword")
						.build();
		
		return user;
	}
	
	public static Post createTestPostA(Set<Tag> tags, Category category, User user)
	{
		Post post = Post.builder()
						.title("TestPostA")
						.content("Contents of test post A...")
						.author(user)
						.category(category)
						.tags(tags)
						.postStatus(PostStatus.PUBLISHED)
						.build();
		
		return post;
	}
	
	public static Post createTestPostB(Set<Tag> tags, Category category, User user)
	{
		Post post = Post.builder()
						.title("TestPostB")
						.content("Contents of test post B...")
						.author(user)
						.category(category)
						.tags(tags)
						.postStatus(PostStatus.DRAFT)
						.build();
		
		return post;
	}
}
