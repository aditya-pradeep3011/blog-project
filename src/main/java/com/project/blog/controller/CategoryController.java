package com.project.blog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.domain.Category;
import com.project.blog.dto.CategoryRequest;
import com.project.blog.dto.CategoryResponse;
import com.project.blog.mapper.CategoryMapper;
import com.project.blog.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories()
	{
		List<CategoryResponse> list = categoryService.getAllCategories().stream().map(categoryMapper::toDto).toList();
		return new ResponseEntity<List<CategoryResponse>>(list, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest)
	{
		Category resp = categoryService.addCategory(categoryMapper.toEntity(categoryRequest));
		return new ResponseEntity<CategoryResponse>(categoryMapper.toDto(resp), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id)
	{
		categoryService.deleteCategory(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
