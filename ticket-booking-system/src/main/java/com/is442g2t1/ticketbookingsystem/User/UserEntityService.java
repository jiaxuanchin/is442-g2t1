package com.is442g2t1.ticketbookingsystem.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Service
public class UserEntityService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final String passwordEncoderUrl = "http://localhost:8080/api/auth/encode_password";

    @Autowired
    public UserEntityService(UserRepository userRepository, RoleService roleService, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.restTemplate = restTemplate;
    }

    // Method to create a new user with different roles
    // public ResponseEntity<?> createUser(String roleName, String user_fname, String user_lname, String email, String password) {
    //     ResponseEntity<?> roleResponse = roleService.getRoleByName(roleName);
    //     if (roleResponse.getStatusCode().is2xxSuccessful()) {
    //         Role role = (Role) roleResponse.getBody();
    //         UserEntity newUser = new UserEntity(role, user_fname, user_lname, email, password); 
    //         return ResponseEntity.ok(userRepository.save(newUser));
    //     } else {
    //         return roleResponse;
    //     }
    // }

    // Method to update a user's profile details
    public ResponseEntity<?> updateUserProfile(int userId, String user_fname, String user_lname, String email) {
        try{
            UserEntity existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

            existingUser.setUser_fname(user_fname);
            existingUser.setUser_lname(user_lname);
            existingUser.setEmail(email);

            UserEntity updatedUser = userRepository.save(existingUser);

            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Fetch the information of the users --> firstname, lastname, email, phone
    public ResponseEntity<?> getUserEntityInfo(int userId){
        try {
            UserEntity existingUserEntity = (UserEntity) userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

            System.out.println("[CHECKPOINT UserEntityService] GET USER INFO: " + existingUserEntity.toString());
            return ResponseEntity.ok(existingUserEntity);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }

    // Method to retrieve a user by email
    public ResponseEntity<?> getUserByEmail(String email) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            UserEntity user = userOptional.get();
            return ResponseEntity.ok(user);

            // return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<?> createTicketingOfficer(String user_fname, String user_lname, String email, String password) {

        System.out.println("CHECKPOINT1");
        
        ResponseEntity<?> existingUserResponse = getUserByEmail(email);
        if (existingUserResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with email " + email + " already exists");
        }

        ResponseEntity<?> roleResponse = roleService.getRoleByName("ticketing_officer");
        if (!roleResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Role 'ticketing_officer' not found");
        }

        // Encode the password using the password encoder API
        ResponseEntity<String> encodeResponse = restTemplate.postForEntity(passwordEncoderUrl, password, String.class);
        if (!encodeResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password encoding failed");
        }

        String encodedPassword = encodeResponse.getBody();

        Role role = (Role) roleResponse.getBody();
        TicketingOfficer newTicketingOfficer = new TicketingOfficer(user_fname, user_lname, email, encodedPassword);
        // TicketingOfficer newTicketingOfficer = new TicketingOfficer(user_fname, user_lname, email, password);
        newTicketingOfficer.setRole(role);
        return ResponseEntity.ok(userRepository.save(newTicketingOfficer));
    }
}
