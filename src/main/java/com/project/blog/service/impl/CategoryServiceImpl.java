package com.project.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.blog.domain.Category;
import com.project.blog.repo.CategoryRepository;
import com.project.blog.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAllWithPostCount();
	}

	@Override
	public Category addCategory(Category category) {
		if(categoryRepository.existsByNameIgnoreCase(category.getName()))
		{
			throw new IllegalArgumentException("Category already exists!");
		}
		
		return categoryRepository.save(category);
	}

}
