package com.jsp.ecommerce_application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jsp.ecommerce_application.repo.AccessTokenRepository;
import com.jsp.ecommerce_application.repo.RefreshTokenRepo;
import com.jsp.ecommerce_application.repo.UserRepository;
import com.jsp.ecommerce_application.security_filter.JwtAuthFilter;
import com.jsp.ecommerce_application.security_filter.LoginFilter;
import com.jsp.ecommerce_application.security_filter.RefreshFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private final JwtService jwtService;
	private final RefreshTokenRepo refreshTokenRepo;
	private final AccessTokenRepository accessTokenRepo;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}



	@Bean
	@Order(1)
	SecurityFilterChain loginSecurityFilterChain(HttpSecurity httpSecurity) throws Exception  {
		return httpSecurity.csrf(csrf-> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers("/api/v3/login/**", "/api/v3/sellers/registers/**", "/api/v3/customers/registers/**"))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new LoginFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain refreshLoginFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf(csrf -> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers("/api/v3/refreshLogin/**"))
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new RefreshFilter(jwtService,refreshTokenRepo), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	@Order(3)
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf(csrf -> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers("/api/v3/**"))
				.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtAuthFilter(jwtService, refreshTokenRepo, accessTokenRepo), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

		return authenticationConfiguration.getAuthenticationManager() ;
	}


}


