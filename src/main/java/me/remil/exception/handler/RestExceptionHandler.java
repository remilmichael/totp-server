package me.remil.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.remil.dto.ErrorResponse;
import me.remil.exception.InvalidEntityException;
import me.remil.exception.MissingItemException;
import me.remil.exception.UnauthorizedRequestException;
import me.remil.exception.UserAlreadyExistsException;

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
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnauthorizedRequestException.class)
	public ResponseEntity<ErrorResponse> handleException(UnauthorizedRequestException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidEntityException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidEntityException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
