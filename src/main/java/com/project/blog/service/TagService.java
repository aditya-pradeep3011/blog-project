package com.project.blog.service;

import java.util.List;
import java.util.Set;

import com.project.blog.domain.Tag;

public interface TagService {
	
	List<Tag> getAllTags();
	
	List<Tag> createTags(Set<String> tagNames);
}
