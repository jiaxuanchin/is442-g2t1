package com.is442g2t1.ticketbookingsystem.security.service;

import com.is442g2t1.ticketbookingsystem.User.Customer;
import com.is442g2t1.ticketbookingsystem.User.Role;
import com.is442g2t1.ticketbookingsystem.User.RoleRepository;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;
import com.is442g2t1.ticketbookingsystem.User.UserRepository;
import com.is442g2t1.ticketbookingsystem.security.DTO.AuthResponseDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.LoginDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.RegisterDTO;
import com.is442g2t1.ticketbookingsystem.security.jwt.JWTGenerator;
import com.is442g2t1.ticketbookingsystem.security.token.Token;
import com.is442g2t1.ticketbookingsystem.security.token.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                        RoleRepository roleRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public ResponseEntity<?> signin(LoginDTO loginRequest) {
        try {
            
            String email = loginRequest.getEmail();

            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
                email,
                loginRequest.getPassword());

            // ----------------------------- CHECKPOINT -----------------------------
            System.out.println("[CHECKPOINT AuthService] PRINT USER AUTH DETAILS: " + userAuthToken);
            // -----------------------------------------------------------------------

            // trying to authenticate the provided Authentication object 
            Authentication authentication = authenticationManager.authenticate(userAuthToken);

            SecurityContextHolder.getContext().setAuthentication(authentication); 
            String token = jwtGenerator.generateToken(authentication);

            UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

            // revoke any existing tokens before saving the new token to the db
            revokeAllUserTokens(user);

            saveUserToken(user, token);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(item -> item.getAuthority())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            // return new ResponseEntity.ok(new AuthResponseDTO(token), HttpStatus.OK);
            return ResponseEntity.ok(new AuthResponseDTO(token, 
                userDetails.getId(), 
                userDetails.getUsername(), 
                userDetails.getEmail(), 
                role));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    public ResponseEntity<String> signup(RegisterDTO registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepository.findByName("customer").orElse(null);

        if (role == null) {
            return new ResponseEntity<>("Role not found!", HttpStatus.BAD_REQUEST);
        }

        try {
            UserEntity user = new Customer(role, registerDto.getUser_fname(),registerDto.getUser_lname(),registerDto.getEmail(), 
                passwordEncoder.encode(registerDto.getPassword())
            );

            System.out.println("[CHECKPOINT AuthController] PRINT USER DETAILS: " + user.toString());

            userRepository.save(user);
            
            return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> verifyPassword(HttpServletRequest request, String password) {

        try {
            String token = jwtGenerator.extractJwtFromRequest(request);
            String email = jwtGenerator.getEmailFromJWT(token);
    
            
            UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    
            boolean result = passwordEncoder.matches(password, user.getPassword());

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void saveUserToken(UserEntity user, String token) {

        Token buildToken = Token.builder()
            .user(user)
            .token(token)
            .revoked(false)
            .expired(false)
            .build();

        tokenRepository.save(buildToken);
    }

    private void revokeAllUserTokens(UserEntity user) {

        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
        
        if (validUserTokens.isEmpty()) {
            return;
            
        }

        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
            // tokenRepository.save(token);
        });

        tokenRepository.saveAll(validUserTokens);
    }

}
