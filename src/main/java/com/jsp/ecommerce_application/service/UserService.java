package com.jsp.ecommerce_application.service;

import org.springframework.http.ResponseEntity;

import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.requestdto.AuthRequest;
import com.jsp.ecommerce_application.requestdto.OtpVerificationRequest;
import com.jsp.ecommerce_application.requestdto.UserRequest;
import com.jsp.ecommerce_application.responsedto.AuthResponse;
import com.jsp.ecommerce_application.responsedto.UserResponse;
import com.jsp.ecommerce_application.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest, UserRole seller);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpVerificationRequest otpVerificationRequest);
 
	ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest);


	

}
