package com.jsp.ecommerce_application.requestdto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRequest {
	
//	@NotNull(message = "email cannot be null")
//	@NotBlank(message = "email cannot be blank")
//	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	
//	@NotNull(message = "password cannot be null")
//	@NotBlank(message = "password cannot be blank")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
//			+ " contain at least one letter, one number, one special character")
	private String password;
	private boolean isEmailVerified;
	private String isDeleted;
}
