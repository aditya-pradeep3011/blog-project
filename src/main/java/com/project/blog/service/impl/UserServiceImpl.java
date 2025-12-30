package com.project.blog.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.blog.domain.User;
import com.project.blog.repo.UserRepository;
import com.project.blog.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	@Override
	public User getUserById(UUID userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID: "+userId+" could not be found!"));
	}

	@Override
	public User createUser(User user) {
		if(userRepository.existsByNameIgnoreCase(user.getName()))
		{
			throw new IllegalArgumentException("User with name "+user.getName()+" already exists!");
		}
		return userRepository.save(user);
	}

}
