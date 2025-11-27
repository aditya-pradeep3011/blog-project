package com.project.blog.service;

import java.util.List;

import com.project.blog.domain.Category;

public interface CategoryService {

	List<Category> getAllCategories();
	
	Category addCategory(Category category);
}
