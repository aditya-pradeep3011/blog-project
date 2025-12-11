package com.project.blog.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.project.blog.domain.Tag;

public interface TagService {
	
	List<Tag> getAllTags();
	
	List<Tag> createTags(Set<String> tagNames);
	
	void deleteTag(UUID id);
}
