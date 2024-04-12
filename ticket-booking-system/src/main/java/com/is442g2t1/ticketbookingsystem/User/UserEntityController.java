package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/UserEntity")
public class UserEntityController {

    private UserEntityService userEntityService;

    @Autowired
    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    // Just for testing: Can comment out, register will do this job
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam String roleName,
            @RequestParam String user_fname,
            @RequestParam String user_lname,
            @RequestParam String email,
            @RequestParam String password) {
        return userEntityService.createUser(roleName, user_fname, user_lname, email, password);
    }

    // This is required: Allow users to update their profile
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable int userId,
            @RequestParam String user_fname,
            @RequestParam String user_lname,
            @RequestParam String email) {
        return userEntityService.updateUserProfile(userId, user_fname, user_lname, email);
    }

    // Fetch the information of the users --> firstname, lastname, email, phone, balance
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserEntityInfo(@PathVariable int userId) {
        return userEntityService.getUserEntityInfo(userId);
    }

    // Retrieve user details (for password validation of e-wallet) using email
    @GetMapping("/get/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return userEntityService.getUserByEmail(email);

    }

}