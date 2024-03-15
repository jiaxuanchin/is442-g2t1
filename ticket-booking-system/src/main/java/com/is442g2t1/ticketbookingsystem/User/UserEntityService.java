package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserEntityService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Method to create a new user with different roles
    public UserEntity createUser(String roleName, String user_fname, String user_lname, String email, String password) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        UserEntity newUser = new UserEntity(role, user_fname, user_lname, email, password);
        return userRepository.save(newUser);
    }

    // Method to update a user's profile details
    public UserEntity updateUserProfile(int userId, String user_fname, String user_lname, String email, String password) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        existingUser.setUser_fname(user_fname);
        existingUser.setUser_lname(user_lname);
        existingUser.setEmail(email);
        existingUser.setPassword(password);

        return userRepository.save(existingUser);
    }

}
