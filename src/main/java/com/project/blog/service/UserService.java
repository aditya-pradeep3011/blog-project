package com.project.blog.service;

import java.util.UUID;

import com.project.blog.domain.User;

public interface UserService {

	User getUserById(UUID userId);
}
