package com.jsp.ecommerce_application.service;

import org.springframework.http.ResponseEntity;

import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.requestdto.AuthRequest;
import com.jsp.ecommerce_application.requestdto.OtpVerificationRequest;
import com.jsp.ecommerce_application.requestdto.UserRequest;
import com.jsp.ecommerce_application.responsedto.AuthResponse;
import com.jsp.ecommerce_application.responsedto.UserResponse;
import com.jsp.ecommerce_application.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest, UserRole seller);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpVerificationRequest otpVerificationRequest);
 
	ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest, String refreshToken, String accessToken);

	ResponseEntity<ResponseStructure<AuthResponse>> refreshlogin(String refreshToken);

	ResponseEntity<ResponseStructure<AuthResponse>> logout(String refreshToken, String accessToken);

	ResponseEntity<com.jsp.ecommerce_application.utility.SimpleStructure> logoutFromOtherDevices(String refreshToken,
			String accessToken);

	ResponseEntity<com.jsp.ecommerce_application.utility.SimpleStructure> logoutFromAllDevices(String accessToken);

}
