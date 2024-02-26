package com.is442g2t1.ticketbookingsystem.repository;

import com.is442g2t1.ticketbookingsystem.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // customised
    Optional<UserEntity> findByemail(String email); // not sure if it is cae sensitive
    Boolean existsByemail(String email); 
}