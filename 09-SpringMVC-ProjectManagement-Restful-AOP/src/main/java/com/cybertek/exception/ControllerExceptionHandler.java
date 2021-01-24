package com.cybertek.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

	  @ExceptionHandler(value = {RoleNotFoundException.class})
	  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	  public ApiErrorResponse roleNotFoundException(RoleNotFoundException ex, WebRequest request) {
		  ApiErrorResponse message = new ApiErrorResponse(
	        400,
	        LocalDateTime.now(),
	        ex.getMessage(),
	        "Please use roles: ROLE_ADMIN, ROLE_MANAGER_ROLE_USER");
	    
	    return message;
	  }
	  
	  @ExceptionHandler(value = {UserAlreadyExistAuthenticationException.class})
	  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	  public ApiErrorResponse userAlreadyExistAuthenticationException(UserAlreadyExistAuthenticationException ex, WebRequest request) {
		  ApiErrorResponse message = new ApiErrorResponse(
	        400,
	        LocalDateTime.now(),
	        ex.getMessage(),
	        "User already exist. Please use different username.");
	    
	    return message;
	  }
	  
	  @ExceptionHandler(value = {UserNotFoundException.class})
	  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	  public ApiErrorResponse userNotFoundException(UserNotFoundException ex, WebRequest request) {
		  ApiErrorResponse message = new ApiErrorResponse(
	        400,
	        LocalDateTime.now(),
	        ex.getMessage(),
	        "User not found. Please use valid username");
	    
	    return message;
	  }
	  
	  @ExceptionHandler(value = {ProjectCodeAlreadyExistException.class})
	  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	  public ApiErrorResponse projectCodeAlreadyExistException(ProjectCodeAlreadyExistException ex, WebRequest request) {
		  ApiErrorResponse message = new ApiErrorResponse(
	        400,
	        LocalDateTime.now(),
	        ex.getMessage(),
	        "Project Code already exist. Please use different project code.");
	    
	    return message;
	  }
	  


	  
	   
}
