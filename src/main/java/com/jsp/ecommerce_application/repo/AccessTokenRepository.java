package com.jsp.ecommerce_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce_application.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

}
