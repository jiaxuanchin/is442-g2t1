package com.is442g2t1.ticketbookingsystem.User;

// import com.is442g2t1.ticketbookingsystem.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // customised
    public Optional<UserEntity> findByEmail(String email); // not sure if it is cae sensitive
    public Boolean existsByEmail(String email); 
}