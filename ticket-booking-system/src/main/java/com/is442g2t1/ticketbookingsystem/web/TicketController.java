package com.is442g2t1.ticketbookingsystem.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.model.*;
import com.is442g2t1.ticketbookingsystem.service.*;
import com.is442g2t1.ticketbookingsystem.repository.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketService = new TicketService(ticketRepository);
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        // Get all tickets, NOTE: DOES NOT RETURN BOOKINGID
        List<Ticket> result = this.ticketService.getAllTickets();
        
        return result;
    }

    @GetMapping("/{ticketId}")
    public Optional<Ticket> getTicketById(@PathVariable int ticketId) {
        // Get all tickets
        Optional<Ticket> result = this.ticketService.getTicketById(ticketId);

        // If ticket does not exist: {"data":Optional.empty} is returned
        return result;
    }

    @GetMapping("/booking/{bookingId}")
    public List<Ticket> getTicketsByBookingId(@PathVariable int bookingId) {
        // Get all tickets
        List<Ticket> result = this.ticketService.getTicketsByBookingId(bookingId);
        return result;
    }

    @PostMapping("/new")
    public String createTicket(@RequestBody Booking booking) {
        // Create a ticket by sending Booking instance
        String result = this.ticketService.createTicket(booking);
        return result;
    }

    @PutMapping("/update/attendance/{ticketId}")
    public Ticket updateTicketAttendance(@PathVariable int ticketId) {
        // Update a ticket by sending Ticket instance
        Ticket result = this.ticketService.updateTicketAttendance(ticketId);
        return result;
    }
    
    // don't need to implement deleteTicket? ideal should be cascading delete
}
