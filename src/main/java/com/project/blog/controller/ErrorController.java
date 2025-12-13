package com.project.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.ApiErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@RestController
public class ErrorController {

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception e)
	{
		ApiErrorResponse response = ApiErrorResponse.builder()
				.name("Exception")
				.message("An unexpected error occurred!")
				.code(500)
				.build();
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(exception = IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e)
	{
		ApiErrorResponse response = ApiErrorResponse.builder()
				.name("IllegalArgumentException")
				.message(e.getMessage())
				.code(400)
				.build();
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException e)
	{
		ApiErrorResponse response = ApiErrorResponse.builder()
				.name("BadCredentialsException")
				.message("Incorrect username or password!")
				.code(401)
				.build();
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(exception = IllegalStateException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException e)
	{
		ApiErrorResponse response = ApiErrorResponse.builder()
				.name("IllegalStateException")
				.message(e.getMessage())
				.code(409)
				.build();
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(exception = EntityNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException e)
	{
		ApiErrorResponse response = ApiErrorResponse.builder()
				.name("EntityNotFoundException")
				.message(e.getMessage())
				.code(404)
				.build();
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
}
