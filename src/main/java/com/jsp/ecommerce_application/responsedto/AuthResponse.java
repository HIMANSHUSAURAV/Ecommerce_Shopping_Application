package com.jsp.ecommerce_application.responsedto;

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
public class AuthResponse {

	private int userId;
	private String userName;
	private long accessExpiration;
	private long refreshExpiration;
}
