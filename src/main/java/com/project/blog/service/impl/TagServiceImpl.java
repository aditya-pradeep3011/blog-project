package com.project.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.blog.domain.Tag;
import com.project.blog.repo.TagRepository;
import com.project.blog.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
	
	private final TagRepository tagRepository;

	@Override
	public List<Tag> getAllTags() {
		return tagRepository.findAllWithPostCount();
	}

}
