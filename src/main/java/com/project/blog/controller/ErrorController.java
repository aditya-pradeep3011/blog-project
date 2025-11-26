package com.project.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.ApiErrorResponse;

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
}
