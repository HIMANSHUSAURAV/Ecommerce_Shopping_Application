package com.jsp.ecommerce_application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.requestdto.AuthRequest;
import com.jsp.ecommerce_application.requestdto.OtpVerificationRequest;
import com.jsp.ecommerce_application.requestdto.UserRequest;
import com.jsp.ecommerce_application.responsedto.AuthResponse;
import com.jsp.ecommerce_application.responsedto.UserResponse;
import com.jsp.ecommerce_application.service.UserService;
import com.jsp.ecommerce_application.utility.ResponseStructure;
import com.jsp.ecommerce_application.utility.SimpleStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	 
	@PostMapping("/sellers/register")
	public ResponseEntity<ResponseStructure<UserResponse>>saveSeller(@Valid @RequestBody UserRequest userRequest){
		return userService.saveUser(userRequest,UserRole.SELLER);
		 
	}
	@PostMapping("/customers/register")
	public ResponseEntity<ResponseStructure<UserResponse>>saveCustomers(@RequestBody UserRequest userRequest){
		return userService.saveUser(userRequest,UserRole.CUSTOMER);
		 
	}
	@PostMapping("/users/otp")
	public ResponseEntity<ResponseStructure<UserResponse>>verifyOtp(@RequestBody OtpVerificationRequest otpVerificationRequest){
		return userService.verifyOtp(otpVerificationRequest);
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(@RequestBody AuthRequest authRequest,
            @CookieValue(name = "rt", required = false) String refreshToken,
            @CookieValue(name = "at", required = false) String accessToken) {
		return userService.login(authRequest, refreshToken, accessToken);
		
	}
	
	@PostMapping("/refreshLogin")
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(
			@CookieValue(value = "rt", required = false) String refreshToken) {
		return userService.refreshlogin(refreshToken);

	}

	
	@GetMapping( "/test") 
	public String  test() {
		return "Success";
	}


	@PostMapping("/logout")
	public ResponseEntity<ResponseStructure<AuthResponse>> logout(
			@CookieValue(value = "rt", required = false) String refreshToken,
			@CookieValue(value = "at", required = false) String accessToken) {
		return userService.logout(refreshToken, accessToken);

	}

	@PostMapping("/logoutFromOtherDevices")

	public ResponseEntity<SimpleStructure> logoutFromOtherDevices(
			@CookieValue(value = "rt", required = false) String refreshToken,
			@CookieValue(value = "at", required = false) String accessToken) {
		return userService.logoutFromOtherDevices(refreshToken, accessToken);
	}

	@PostMapping("/logoutAll")
	public ResponseEntity<SimpleStructure> logoutFromAllDevices(@CookieValue(value="at",required=false) String accessToken) {
		return userService.logoutFromAllDevices(accessToken);

	}

}



