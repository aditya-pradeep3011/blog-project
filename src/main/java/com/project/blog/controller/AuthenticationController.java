package com.project.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.LoginRequest;
import com.project.blog.dto.TokenResponse;
import com.project.blog.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest)
	{
		UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		String tokenValue = authenticationService.generateToken(userDetails);
		TokenResponse tokenResponse = TokenResponse.builder()
				.token(tokenValue)
				.expiresIn(86400L)
				.build();
		
		return new ResponseEntity<TokenResponse>(tokenResponse, HttpStatus.OK);
	}
}
