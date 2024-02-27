package com.is442g2t1.ticketbookingsystem.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    public List<Booking> findByUserId(int userId);
    public List<Booking> findByEventId(int eventId);
}
