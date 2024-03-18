package com.is442g2t1.ticketbookingsystem.security.jwt;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
//import java.security.KeyPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {
	// private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // This key is used to sign and verify JWT tokens
	
	public String generateToken(Authentication authentication) {
		String email = authentication.getName(); //https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html
		
		@Value("${JWT_Secret}")
		private final String jwtSecret;
	  
		@Value("${JWT_Expiration_Ms}")
		private final int jwtExpirationMs;
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + this.jwtExpirationMs);
		
		String token = Jwts.builder()
				.setSubject(email)
				.setIssuedAt( new Date())
				.setExpiration(expireDate)
				.signWith(key,SignatureAlgorithm.HS512)
				.compact();
		System.out.println("New token :");
		System.out.println(token);
		return token;
	}

	public String getUsernameFromJWT(String token){
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject(); // retrieve the subject claim, which represents the username associated with the token
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}

}