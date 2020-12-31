package com.cybertek.exception;

import java.time.LocalDateTime;

public class ApiErrorResponse {

	 private int statusCode;
	  private LocalDateTime timestamp;
	  private String message;
	  private String description;

	  public ApiErrorResponse(int statusCode, LocalDateTime timestamp, String message, String description) {
	    this.statusCode = statusCode;
	    this.timestamp = timestamp;
	    this.message = message;
	    this.description = description;
	  }

	  public int getStatusCode() {
	    return statusCode;
	  }

	  public LocalDateTime getTimestamp() {
	    return timestamp;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public String getDescription() {
	    return description;
	  }
	
}
