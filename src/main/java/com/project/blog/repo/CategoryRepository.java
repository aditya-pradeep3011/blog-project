package com.project.blog.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{

	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
	public List<Category> findAllWithPostCount();
	
	public boolean existsByNameIgnoreCase(String name);
}
