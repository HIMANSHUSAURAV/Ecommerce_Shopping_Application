package com.jsp.ecommerce_application.security_filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.web.filter.OncePerRequestFilter;

import com.jsp.ecommerce_application.exception.UserAlreadyLoggedInException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends OncePerRequestFilter{
	
	boolean loggedIn = false;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		boolean loggedIn = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("at") || cookie.getName().equals("rt")) {
					loggedIn = true;
				}

			}
		}
		if (loggedIn) {
			throw new UserAlreadyLoggedInException("User already logged in");
		}

		else {
			filterChain.doFilter(request, response);
		}

	}

}
