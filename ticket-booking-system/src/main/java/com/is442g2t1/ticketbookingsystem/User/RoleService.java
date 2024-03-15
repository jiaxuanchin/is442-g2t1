package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Method to retrieve a role by name
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElse(null); // Return null if role is not found
    }

}
