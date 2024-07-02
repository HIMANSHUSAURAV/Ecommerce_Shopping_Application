package com.jsp.ecommerce_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce_application.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

}
