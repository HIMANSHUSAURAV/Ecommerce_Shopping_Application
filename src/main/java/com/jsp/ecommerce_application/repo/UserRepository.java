package com.jsp.ecommerce_application.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce_application.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);
	public boolean existsByEmail(String email);

	public Optional<User> findByEmail(String email);
}
