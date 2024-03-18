package com.is442g2t1.ticketbookingsystem.security.jwt;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.is442g2t1.ticketbookingsystem.security.service.UserDetailsImpl;

@Component
public class JWTGenerator {
	// private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
	// private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // This key is used to sign and verify JWT tokens

	private Key key() {
    	return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_Secret));
  	}
	
	public String generateToken(Authentication authentication) {
		// String email = authentication.getName(); //https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html
	
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_Expiration_Ms);

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		String token = Jwts.builder()
				.setSubject(userPrincipal.getEmail())
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(key(),SignatureAlgorithm.HS256)
				.compact();
		System.out.println("New token: " + token);
		return token;
	}

	public String getEmailFromJWT(String token){
		// Claims claims = Jwts.parserBuilder()
		// 		.setSigningKey(key)
		// 		.build()
		// 		.parseClaimsJws(token)
		// 		.getBody();
		// return claims.getSubject(); // retrieve the subject claim, which represents the username associated with the token

		return Jwts.parserBuilder().setSigningKey(key()).build()
					.parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parse(token);
			return true;

		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token: {}" + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT token is expired: {}" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("JWT token is unsupported: {}" + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty: {}" + e.getMessage());
		}

		return false;
	}

}