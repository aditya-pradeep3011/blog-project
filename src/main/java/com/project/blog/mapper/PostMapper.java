package com.project.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.project.blog.domain.Post;
import com.project.blog.dto.PostDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

	@Mapping(source = "author", target = "author")
	@Mapping(source = "category", target = "category")
	@Mapping(source = "tags", target = "tags")
	PostDto toDto(Post post);
}
