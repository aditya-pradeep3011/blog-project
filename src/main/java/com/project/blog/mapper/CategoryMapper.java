package com.project.blog.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.project.blog.domain.Category;
import com.project.blog.domain.Post;
import com.project.blog.domain.PostStatus;
import com.project.blog.dto.CategoryRequest;
import com.project.blog.dto.CategoryResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

	@Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
	public CategoryResponse toDto(Category category);
	
	public Category toEntity(CategoryRequest categoryRequest);
	
	@Named(value = "calculatePostCount")
	default long calculatePostCount(List<Post> posts)
	{
		if(posts.isEmpty())
			return 0;
		
		return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus())).count();
	}
}
