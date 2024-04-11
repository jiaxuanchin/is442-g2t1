package com.is442g2t1.ticketbookingsystem.security.jwt;
import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.is442g2t1.ticketbookingsystem.security.service.UserDetailsImpl;

@Component
public class JWTService {

	private Key key() {
    	return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.JWT_Secret));
  	}
	
	public String generateToken(Authentication authentication) {
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_Expiration_Ms);

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		String token = Jwts.builder()
			.setSubject(userPrincipal.getEmail())
			.claim("user_id", userPrincipal.getId())
			.claim("role_id", userPrincipal.getRole())
			.setIssuedAt(currentDate)
			.setExpiration(expireDate)
			.signWith(key(),SignatureAlgorithm.HS256)
			.compact();

		return token;
	}

	private Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public String getEmailFromJWT(String token){
		// Claims claims = Jwts.parserBuilder()
		// 		.setSigningKey(key)
		// 		.build()
		// 		.parseClaimsJws(token)
		// 		.getBody();
		// return claims.getSubject(); // retrieve the subject claim, which represents the username associated with the token

		Claims claims = extractAllClaims(token);

		String email = claims.getSubject();

		// ----------------------------- CHECKPOINT -----------------------------
		System.out.println("[CHECKPOINT JWTService] Getting email from token: " + email);
		// -----------------------------------------------------------------------

		return email;
	}
	
	public boolean validateToken(String token) {
		try {

			// ----------------------------- CHECKPOINT -----------------------------
			System.out.println("[CHECKPOINT JWTService] Validating token " + token);
			// -----------------------------------------------------------------------

			// check token against signing key from signature
			Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parse(token);

				Claims claims = extractAllClaims(token);

			if (!isTokenExpired(token)) {
				return true;
			} 

			throw new ExpiredJwtException(null, claims, "Token is expired");

		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("JWT token is expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty: " + e.getMessage());
		}

		return false;
	}

	public String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

	public String extractUsername(String token) {

		Claims claims = extractAllClaims(token);
		return claims.get("user_id").toString();
	}

	private Date extractExpiration(String token) {

		Claims claims = extractAllClaims(token);
		return claims.getExpiration();
	}

	private boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	// public String extractRole(String token) {
		
		// Claims claims = extractAllClaims(token);
		// return claims.get("role_id").toString();

	// }

}
