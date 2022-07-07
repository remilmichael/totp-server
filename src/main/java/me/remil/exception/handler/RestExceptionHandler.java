package me.remil.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.SignatureVerificationException;

import me.remil.dto.ErrorResponse;
import me.remil.exception.MissingItemException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(MissingItemException.class)
	public ResponseEntity<ErrorResponse> handleException(MissingItemException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SignatureVerificationException.class)
	public ResponseEntity<ErrorResponse> handleException(SignatureVerificationException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage("User not authorized to perform this action");
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
}
