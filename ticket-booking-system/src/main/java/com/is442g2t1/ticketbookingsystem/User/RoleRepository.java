package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    // customised
    Optional<Role> findByName(String name); 
} 
