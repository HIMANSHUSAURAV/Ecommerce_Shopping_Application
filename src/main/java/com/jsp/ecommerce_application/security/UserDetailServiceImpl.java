package com.jsp.ecommerce_application.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce_application.repo.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		return repository.findByUserName(userName)
				.map(user -> new UserDetailImpl(user))
				.orElseThrow(() -> new UsernameNotFoundException("aunthentication failed"));
	}


}
