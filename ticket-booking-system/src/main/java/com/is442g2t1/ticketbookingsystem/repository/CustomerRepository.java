package com.is442g2t1.ticketbookingsystem.repository;

import com.is442g2t1.ticketbookingsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Custom database queries can be added here
}
