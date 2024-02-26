package com.is442g2t1.ticketbookingsystem.repository;

import com.is442g2t1.ticketbookingsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    // customised
    Optional<Role> findByrole_name(String role_name); 
} 
