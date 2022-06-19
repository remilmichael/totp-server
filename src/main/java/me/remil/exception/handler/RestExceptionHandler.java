package me.remil.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.remil.dto.ErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
}
