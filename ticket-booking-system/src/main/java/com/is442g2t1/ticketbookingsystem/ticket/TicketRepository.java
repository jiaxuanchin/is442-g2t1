package com.is442g2t1.ticketbookingsystem.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.is442g2t1.ticketbookingsystem.booking.Booking;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    public List<Ticket> findTicketsByBooking(Booking booking);

}
