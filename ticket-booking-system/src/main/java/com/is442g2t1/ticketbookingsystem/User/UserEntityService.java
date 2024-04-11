package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Service
public class UserEntityService {

    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public UserEntityService(UserRepository userRepository, RoleService roleService){
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    // Method to create a new user with different roles
    public ResponseEntity<?> createUser(String roleName, String user_fname, String user_lname, String email, String password) {
        ResponseEntity<?> roleResponse = roleService.getRoleByName(roleName);
        if (roleResponse.getStatusCode().is2xxSuccessful()) {
            Role role = (Role) roleResponse.getBody();
            UserEntity newUser = new UserEntity(role, user_fname, user_lname, email, password); 
            return ResponseEntity.ok(userRepository.save(newUser));
        } else {
            return roleResponse;
        }
    }

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
        } catch (IllegalArgumentException e ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + userId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Fetch the information of the users --> firstname, lastname, email, phone
    public ResponseEntity<?> getUserEntityInfo(int userId){
        try {
            UserEntity existingUserEntity = (UserEntity) userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            return ResponseEntity.ok(existingUserEntity);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }

}
