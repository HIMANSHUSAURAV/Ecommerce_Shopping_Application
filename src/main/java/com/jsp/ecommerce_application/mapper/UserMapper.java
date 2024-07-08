package com.jsp.ecommerce_application.mapper;

import org.springframework.stereotype.Component;

import com.jsp.ecommerce_application.entity.User;
import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.requestdto.UserRequest;
import com.jsp.ecommerce_application.responsedto.UserResponse;

@Component
public class UserMapper {
	
	public User mapToUser(UserRequest userRequest, User user) {
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		return user;
		
	}

	public  UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.isEmailVerified(user.isEmailVerified())
				.build();
	}
}
