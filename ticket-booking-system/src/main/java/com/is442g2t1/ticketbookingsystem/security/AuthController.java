package com.is442g2t1.ticketbookingsystem.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.is442g2t1.ticketbookingsystem.security.DTO.LoginDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.RegisterDTO;
import com.is442g2t1.ticketbookingsystem.security.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/verify_password")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> verifyPassword(HttpServletRequest request, @RequestBody String password) {

        System.out.println("[CHECKPOINT AuthController] PASSWORD: " + password);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(password);
            String pwd = rootNode.get("password").textValue();

            System.out.println("Password: " + pwd);
            return authService.verifyPassword(request, pwd);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // change password, post method /change_password
    @PostMapping("/change_password")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody String password) {
        // return authService.changePassword(request, password.substring(1, password.length()-1));

        System.out.println("[CHECKPOINT AuthController] (CHANGE PASSWORD) PASSWORD: " + password);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(password);
            String pwd = rootNode.get("newPassword").textValue();

            System.out.println("Password: " + pwd);
            return authService.changePassword(request, pwd);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // // /add-ticketing-officer, post method
    // @PostMapping("/add-ticketing-officer")
    // @PreAuthorize("hasAnyAuthority('event_manager')")
    // public ResponseEntity<?> addTicketingOfficer(HttpServletRequest request, @RequestBody RegisterDTO registerDto) {
    //     return authService.addTicketingOfficer(request, registerDto);
    // }

    @PostMapping("/encode_password")
    @PreAuthorize("hasAnyAuthority('event_manager')")
    public ResponseEntity<?> encodePassword(@RequestBody String password) {
        return authService.encodePassword(password.substring(1, password.length()-1));
        
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        return authService.getUser(userId);
    }

}
