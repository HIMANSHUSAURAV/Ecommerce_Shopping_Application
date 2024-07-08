package com.jsp.ecommerce_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAlreadyLoggedInException extends RuntimeException {

	private String message;
	
}
