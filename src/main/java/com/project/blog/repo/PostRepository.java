package com.project.blog.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

}
