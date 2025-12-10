package com.project.blog.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.project.blog.domain.Post;
import com.project.blog.domain.PostStatus;
import com.project.blog.domain.Tag;
import com.project.blog.dto.TagResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

	@Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
	TagResponse toDto(Tag tag);
	
	@Named("calculatePostCount")
	default long calculatePostCount(List<Post> postsList)
	{
		if(postsList.isEmpty())
			return 0;
		
		return postsList.stream()
				.filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus()))
				.count();
	}
}
