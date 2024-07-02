package com.jsp.ecommerce_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidJwtTokenException extends RuntimeException {

	private String message;
	
	
	
}
