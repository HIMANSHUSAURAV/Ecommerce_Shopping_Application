package com.jsp.ecommerce_application.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce_application.entity.RefreshToken;
import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.repo.RefreshTokenRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${application.jwt.secret}")
	private String secret;
	
	private RefreshTokenRepo refreshTokenRepo;
	
	private static final String ROLE ="role";
	

	public JwtService(RefreshTokenRepo refreshTokenRepo) {
		super();
		this.refreshTokenRepo = refreshTokenRepo;
	}

	public String createJwtToken(String userName,long expirationTimeInMillies,String role ) {
		return Jwts.builder().setClaims(Map.of(ROLE,role))
				
		 .setSubject(userName)
		 .setIssuedAt(new Date(System.currentTimeMillis()))
		 .setExpiration(new Date(System.currentTimeMillis()+expirationTimeInMillies))
		 
		 .signWith(getSignInKey(), SignatureAlgorithm.HS512)
		 .compact();
		 
		 }
	 
	 private Key getSignInKey() {
		 return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	 }
	 
	 private Claims parseJwtToken(String token) {
		return Jwts.parserBuilder()	
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	 }
	 
	 public String extractUserName(String token) {
		 return parseJwtToken(token).getSubject();
	 }
	 
	 public Date extractIssuedDate(String token) {
		 return parseJwtToken(token).getIssuedAt();
	 }
	 
	 public Date extractExpireDate(String token) {
		return parseJwtToken(token).getExpiration();
	 }


	public String extractUserRole(String token) {
		// TODO Auto-generated method stub
		return parseJwtToken(token).get(ROLE,String.class);
	}

	public boolean isTokenValid(String token) {
		// TODO Auto-generated method stub
		Optional<RefreshToken> refreshToken = refreshTokenRepo.findByRefreshToken(token);
		RefreshToken refreshToken1=refreshToken.get();
		return parseJwtToken(token).getExpiration()
				.after(new Date()) && refreshToken1 != null && !refreshToken1.isBlocked();
	}


}




