package com.is442g2t1.ticketbookingsystem.security;

import com.is442g2t1.ticketbookingsystem.security.DTO.LoginDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.RegisterDTO;
import com.is442g2t1.ticketbookingsystem.security.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ------------------------ TESTER METHODS (TO BE REMOVED) ------------------------
    @GetMapping("/all1")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager')")
    public String userAccess() {
        return "User Content.";
    }
    // -------------------------------------------------------------------------------

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginDTO loginRequest){
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html#authenticate(org.springframework.security.core.Authentication)
        try {
            return authService.signin(loginRequest);
            
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterDTO registerDto) {

        try {
            return authService.signup(registerDto); // frontend to redirct them to login page to login
        
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/verify_password")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> verifyPassword(HttpServletRequest request, @RequestBody String password) {

        System.out.println("[CHECKPOINT AuthController] PASSWORD: " + password.substring(1, password.length()-1));

        return authService.verifyPassword(request, password.substring(1, password.length()-1));

    }

}
