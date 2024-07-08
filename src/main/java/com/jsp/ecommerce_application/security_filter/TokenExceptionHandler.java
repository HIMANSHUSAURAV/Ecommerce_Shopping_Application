package com.jsp.ecommerce_application.security_filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.ecommerce_application.utility.ErrorStructure;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenExceptionHandler {

	public static void handleException(int status ,HttpServletResponse response, String rootcause) throws IOException
	, StreamWriteException, DatabindException, java.io.IOException{
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value()); // all are JSon object
		ErrorStructure<String> error = new ErrorStructure<String>()
				.setStatus(HttpStatus.UNAUTHORIZED.value())
				.setMessage("failed to aunthenticate")
				.setRootcause("the token is already expired");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), error);
		
	}

}
