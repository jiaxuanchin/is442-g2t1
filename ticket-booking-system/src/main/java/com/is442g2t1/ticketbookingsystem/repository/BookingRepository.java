package com.is442g2t1.ticketbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.is442g2t1.ticketbookingsystem.model.Booking;

@Service
public interface BookingRepository extends JpaRepository<Booking, Integer>{

}
