package com.jsp.ecommerce_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtExpiredException extends Exception {

	private String message;
	
}
