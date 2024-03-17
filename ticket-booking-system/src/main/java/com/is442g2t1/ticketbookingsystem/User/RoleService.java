package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Method to retrieve a role by name
    public ResponseEntity<?> getRoleByName(String roleName) {
        try {
            Role role = roleRepository.findByName(roleName).orElse(null);
            if (role != null) {
                return ResponseEntity.ok(role);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: " + roleName);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
