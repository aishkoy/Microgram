package com.suslike.web.exception.handler;

import freemarker.core.InvalidReferenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchElementException.class)
	private ErrorResponse noSuchElementHandler(NoSuchElementException ex) {
		log.error(ex.getMessage());
		return ErrorResponse.builder(ex, HttpStatus.NO_CONTENT, ex.getMessage()).build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse ValidationHandler(MethodArgumentNotValidException ex) {
		log.error(ex.getMessage());
		return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResponse AlreadyExistsException(IllegalArgumentException ex) {
		log.error(ex.getMessage());
		return ErrorResponse.builder(ex, HttpStatus.CONFLICT, ex.getMessage()).build();
	}
}
