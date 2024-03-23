package com.is442g2t1.ticketbookingsystem.security.jwt;

// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.is442g2t1.ticketbookingsystem.security.service.UserDetailsServiceImpl;

import org.springframework.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

// serves as a filter in the Spring Security filter chain responsible for JWT-based authentication. 
// It extracts JWT tokens from incoming HTTP requests, validates them, and sets the authentication token in the security 
// context if the token is valid.

public class JWTAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Logic of the filtering: HTTP requests, extracts JWT tokens, validates them, and sets the authentication token in the security context if the token is valid.
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getJWTFromRequest(request);

            // ----------------------------- CHECKPOINT -----------------------------
            System.out.println("[CHECKPOINT JWTAuthFilter] Validating token: " + token);
            // -----------------------------------------------------------------------

            if(token != null && tokenGenerator.validateToken(token)) {
                String email = tokenGenerator.getEmailFromJWT(token);
    
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                System.out.println("[CHECKPOINT JWTAuthFilter] User authenticated: " + SecurityContextHolder.getContext().getAuthentication());

            } else {
                System.out.println("Token is not valid");
            }

        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);

        }

        filterChain.doFilter(request, response);
        System.out.println("[CHECKPOINT JWTAuthFilter] Filter chain done");
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // ----------------------------- CHECKPOINT -----------------------------
        System.out.println("[CHECKPOINT JWTAuthFilter] Bearer token: " + bearerToken);
        // -----------------------------------------------------------------------

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

}
