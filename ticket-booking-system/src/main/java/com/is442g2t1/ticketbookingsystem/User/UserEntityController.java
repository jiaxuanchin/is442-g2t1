package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.is442g2t1.ticketbookingsystem.security.jwt.JWTService;

import jakarta.servlet.http.HttpServletRequest;

// import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://127.0.0.1:5173","http://localhost:5173"})
@RestController
@RequestMapping("/UserEntity")
public class UserEntityController {

    private UserEntityService userEntityService;

    private JWTService jwtGenerator;

    @Autowired
    public UserEntityController(UserEntityService userEntityService, JWTService jwtGenerator) {
        this.userEntityService = userEntityService;
        this.jwtGenerator = jwtGenerator;
    }

    // Just for testing: Can comment out, register will do this job
    // @PostMapping("/create")
    // public ResponseEntity<?> createUser(@RequestParam String roleName,
    //                                      @RequestParam String user_fname,
    //                                      @RequestParam String user_lname,
    //                                      @RequestParam String email,
    //                                      @RequestParam String password) {
    //     return userEntityService.createUser(roleName, user_fname, user_lname, email, password);
    // }

    // This is required: Allow users to update their profile
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable int userId,
                                                @RequestParam String user_fname,
                                                @RequestParam String user_lname,
                                                @RequestParam String email) {
        return userEntityService.updateUserProfile(userId, user_fname, user_lname, email);
    }

    // Fetch the information of the users --> firstname, lastname, email, phone, balance
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserEntityInfo(@PathVariable int userId) {
        System.out.println("[CHECKPOINT UserEntityController] GET USER INFO: " + userId);

        return userEntityService.getUserEntityInfo(userId);
    }

    // Retrieve user details (for password validation of e-wallet) using email
    @GetMapping("/get/{email}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        // return userEntityService.getUserByEmail(email);

        try {
            ResponseEntity user = userEntityService.getUserByEmail(email);

            return user;

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }

    }

    @PostMapping("/createTicketingOfficer")
    public ResponseEntity<?> createTicketingOfficer(HttpServletRequest request, @RequestBody TicketingOfficer ticketingOfficer) {
        try {
            String token = jwtGenerator.extractJwtFromRequest(request);
            System.out.println("[CHECKPOINT UserEntityController] CREATE TICKETING OFFICER: " + ticketingOfficer.toString());

            return userEntityService.createTicketingOfficer(token, ticketingOfficer.getUser_fname(), ticketingOfficer.getUser_lname(), ticketingOfficer.getEmail(), ticketingOfficer.getPassword());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
