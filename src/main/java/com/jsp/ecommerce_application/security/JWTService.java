package com.jsp.ecommerce_application.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce_application.enums.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	   @Value("${application.jwt.secret}")
	    private String secret;
	   
	   public static final String ROLE = "role";
	
//	private String secret = "WyLOAJB4mh3KZ++NY2c33XGDIApyhQdQKgYVv2BJcnplIHt9fDG7IBqJD/1PJQgmwiMBYzO/wBL4nEveMeyKhA==";

	public String createJWTTocken( String username , long expirationDurationInMillis , String role  ) {
		return Jwts.builder()
				.setClaims(Map.of(ROLE, role))
				.setSubject(username)
				.setIssuedAt( new Date( System.currentTimeMillis()))
				.setExpiration(new Date( System.currentTimeMillis()+expirationDurationInMillis))
				.signWith(getSignatureKey(),SignatureAlgorithm.HS512)
				.compact();

	}

	private  Key getSignatureKey() {
		return Keys.hmacShaKeyFor( Decoders.BASE64.decode(secret));

	}

	private  Claims	praseJWT( String token ) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignatureKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	
	public  UserRole  extractRole(String token) {
		return UserRole.valueOf(praseJWT(token).get(ROLE, String.class));
	}

	
	public String  extractUsername( String token ) {
		return praseJWT(token).getSubject();
	}

	public Date  issuedDate( String token) {  
		return praseJWT(token).getIssuedAt();
	}

	public Date expailationDate( String token) {
		return praseJWT(token).getExpiration();
	}

}




