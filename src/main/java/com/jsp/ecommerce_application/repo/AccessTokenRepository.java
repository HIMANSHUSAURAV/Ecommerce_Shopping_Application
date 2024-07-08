package com.jsp.ecommerce_application.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ecommerce_application.entity.AccessToken;
import com.jsp.ecommerce_application.entity.User;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

	Optional<AccessToken> findByToken(String accessToken);

	List<AccessToken> findByUserAndIsBlocked(User user, boolean b);

	List<AccessToken> findByUserAndIsBlockedAndTokenNot(User user, boolean b, String accessToken);

	List<AccessToken> findAllByExpirationBefore(LocalDateTime now);

}
