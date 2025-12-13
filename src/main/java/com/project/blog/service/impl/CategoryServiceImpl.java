package com.project.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.blog.domain.Category;
import com.project.blog.repo.CategoryRepository;
import com.project.blog.service.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAllWithPostCount();
	}

	@Transactional
	@Override
	public Category addCategory(Category category) {
		if(categoryRepository.existsByNameIgnoreCase(category.getName()))
		{
			throw new IllegalArgumentException("Category already exists!");
		}
		
		return categoryRepository.save(category);
	}

	@Transactional
	@Override
	public void deleteCategory(UUID id) {
		
		Optional<Category> category = categoryRepository.findById(id);
		
		if(category.isPresent())
		{
			if(!category.get().getPosts().isEmpty())
			{
				throw new IllegalStateException("Cannot delete a category that has posts associated with it!");
			}
			categoryRepository.delete(category.get());
		}
		
	}

	@Override
	public Category findById(UUID id) {
		return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with ID: "+id+" could not be found!"));
	}

}
