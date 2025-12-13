package com.project.blog.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.Category;
import com.project.blog.domain.Post;
import com.project.blog.domain.PostStatus;
import com.project.blog.domain.Tag;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

	List<Post> findAllByPostStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
	List<Post> findAllByPostStatusAndCategory(PostStatus status, Category category);
	List<Post> findAllByPostStatusAndTagsContaining(PostStatus status, Tag tag);
	List<Post> findAllByPostStatus(PostStatus status);
}
