package com.jsp.ecommerce_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenExpiredException extends RuntimeException {

	String message;

}
