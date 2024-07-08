package com.jsp.ecommerce_application.security_filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.ecommerce_application.utility.ErrorStructure;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenExceptionHandler {

	public void tokenException(int status,HttpServletResponse response,String rootCause) throws IOException, DatabindException, java.io.IOException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		ErrorStructure<String> error=new ErrorStructure<String>()
				.setStatus(HttpStatus.UNAUTHORIZED.value())
				.setMessage("Failed to authenticate")
				.setRootcause("Token is not valid");
		new ObjectMapper().writeValue(response.getOutputStream(), error);
	}


}
