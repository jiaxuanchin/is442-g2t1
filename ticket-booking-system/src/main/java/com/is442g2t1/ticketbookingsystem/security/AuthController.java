package com.is442g2t1.ticketbookingsystem.security;

import com.is442g2t1.ticketbookingsystem.security.DTO.LoginDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.RegisterDTO;
import com.is442g2t1.ticketbookingsystem.security.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
            return authService.signup(registerDto);
        
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

    // change password, post method /change_password
    @PostMapping("/change_password")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody String password) {
        return authService.changePassword(request, password.substring(1, password.length()-1));

    }

    // /add-ticketing-officer, post method
    @PostMapping("/add-ticketing-officer")
    @PreAuthorize("hasAnyAuthority('event_manager')")
    public ResponseEntity<?> addTicketingOfficer(HttpServletRequest request, @RequestBody RegisterDTO registerDto) {
        return authService.addTicketingOfficer(request, registerDto);
    }

}