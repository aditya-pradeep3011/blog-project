package com.project.blog.repo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{

	@Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts")
	public List<Tag> findAllWithPostCount();
	
	public List<Tag> findByNameIn(Set<String> names);
}
