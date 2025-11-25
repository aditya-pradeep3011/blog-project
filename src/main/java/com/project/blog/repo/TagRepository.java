package com.project.blog.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{

}
