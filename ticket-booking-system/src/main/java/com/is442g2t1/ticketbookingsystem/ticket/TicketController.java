package com.is442g2t1.ticketbookingsystem.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.is442g2t1.ticketbookingsystem.booking.Booking;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"})
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity getAllTickets() {

        ResponseEntity result = this.ticketService.getAllTickets();
        // System.out.println("RESULT:" + result.getStatusCode().equals(HttpStatus.OK));
        return result;
    }

    @GetMapping("/{ticketId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity getTicketById(@PathVariable int ticketId) {

        ResponseEntity result = this.ticketService.getTicketById(ticketId);
        return result;
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity getTicketsByBookingId(@PathVariable int bookingId) {

        ResponseEntity result = this.ticketService.getTicketsByBookingId(bookingId);
        return result;
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity createTicket(@RequestBody Booking bookingId) {

        // Create a ticket by sending Ticket instance
        Ticket ticket = new Ticket(bookingId);
        System.out.println("[CHECKPOINT: CREATE TICKET]: " + ticket.toString());
        ResponseEntity result = this.ticketService.createTicket(ticket);
        
        return result;
    }

    @PutMapping("/update/attendance/{ticketId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity updateTicketAttendance(@PathVariable int ticketId) {
        // Update a ticket by sending Ticket instance
        System.out.println("[CHECKPOINT: UPDATE TICKET ATTENDANCE]: " + ticketId);
        ResponseEntity result = this.ticketService.updateTicketAttendance(ticketId);
        return result;

    }

    // don't need to implement deleteTicket? ideal should be cascading delete
    // @DeleteMapping("/delete/{ticketId}")
    // public String deleteTicket(@PathVariable int ticketId) {
    //     // Delete a ticket by sending Ticket instance
    //     String result = this.ticketService.deleteTicket(ticketId);
    //     return result;

    // }
}