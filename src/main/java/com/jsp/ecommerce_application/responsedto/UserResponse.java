package com.jsp.ecommerce_application.responsedto;

import com.jsp.ecommerce_application.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

	private int userId;
	private String userName;
	private String email;
	private UserRole userRole;
	private boolean isEmailVerified;
	private boolean isDeleted;


}
