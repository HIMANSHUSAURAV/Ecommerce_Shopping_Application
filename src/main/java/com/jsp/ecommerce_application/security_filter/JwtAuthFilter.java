package com.jsp.ecommerce_application.security_filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.security.JWTService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	  private final JwtExceptionHandler exceptionHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		String token = request.getHeader("Authorization");

		if (token != null) {
			System.out.println("inside jwt file ");
			//token = token.substring(7);
			System.out.println(token);

			try {

				String username = jwtService.extractUsername(token);

				UserRole userRole = jwtService.extractRole(token);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, List.of(new SimpleGrantedAuthority(userRole.name().toString())));
					
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


				}
			} catch (JwtException ex) {
				
				exceptionHandler.handleException((response,
                        HttpStatus.UNAUTHORIZED.value(),
                        "Failed to authenticate",
                        "Invalid token");
			}
			

			try {

				Date expailationDate = jwtService.expailationDate(token);

			} catch (ExpiredJwtException ex) {
				ex.printStackTrace();
				// throw new JwtExpiredException("any message");

			}
			System.out.println(token);
		}

		filterChain.doFilter(request, response);
	}

}
