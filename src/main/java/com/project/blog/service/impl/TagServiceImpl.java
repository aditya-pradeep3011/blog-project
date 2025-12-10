package com.project.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.blog.domain.Tag;
import com.project.blog.repo.TagRepository;
import com.project.blog.service.TagService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
	
	private final TagRepository tagRepository;

	@Override
	public List<Tag> getAllTags() {
		return tagRepository.findAllWithPostCount();
	}

	@Transactional
	@Override
	public List<Tag> createTags(Set<String> tagNames) {
		
		List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
		
		Set<String> existingTagNames = existingTags.stream()
												   .map(Tag::getName)
												   .collect(Collectors.toSet());
		
		List<Tag> newTags = tagNames.stream()
									   .filter(name -> !existingTagNames.contains(name))
									   .map(name -> Tag.builder()
											   		   .name(name)
											   		   .build())
									   .toList();

		List<Tag> createdTags = new ArrayList<>();
		if(!newTags.isEmpty())
		{
			createdTags = tagRepository.saveAll(newTags);
		}
		
		createdTags.addAll(existingTags);
		return createdTags;
	}

}
