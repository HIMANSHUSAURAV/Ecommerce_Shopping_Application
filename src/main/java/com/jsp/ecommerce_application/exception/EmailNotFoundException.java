package com.jsp.ecommerce_application.exception;

public class EmailNotFoundException extends RuntimeException {

	
	private String message;

	public EmailNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	
}
