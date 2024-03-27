package com.is442g2t1.ticketbookingsystem.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.is442g2t1.ticketbookingsystem.security.token.Token;
import com.is442g2t1.ticketbookingsystem.security.token.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private TokenRepository tokenRepository;

    @Autowired
    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    
    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) 
    {
        String bearerToken = request.getHeader("Authorization");

        // ----------------------------- CHECKPOINT -----------------------------
        System.out.println("[CHECKPOINT LogoutService] Bearer token: " + bearerToken);
        // -----------------------------------------------------------------------

        if(!(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))) {
            return;
        }

        String jwt = bearerToken.substring(7, bearerToken.length());

        Token token = tokenRepository.findByToken(jwt)
            .orElse(null);

        if (token != null) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }
}
