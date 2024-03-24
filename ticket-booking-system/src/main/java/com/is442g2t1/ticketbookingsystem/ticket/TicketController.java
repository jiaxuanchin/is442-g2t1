package com.is442g2t1.ticketbookingsystem.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.booking.Booking;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllTickets() {

        ResponseEntity result = this.ticketService.getAllTickets();
        // System.out.println("RESULT:" + result.getStatusCode().equals(HttpStatus.OK));
        return result;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity getTicketById(@PathVariable int ticketId) {

        ResponseEntity result = this.ticketService.getTicketById(ticketId);
        return result;
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity getTicketsByBookingId(@PathVariable int bookingId) {

        ResponseEntity result = this.ticketService.getTicketsByBookingId(bookingId);
        return result;
    }

    @PostMapping("/new")
    public ResponseEntity createTicket(@RequestBody Ticket ticket) {
        // Create a ticket by sending Ticket instance
        ResponseEntity result = this.ticketService.createTicket(ticket);
        return result;
    }

    @PutMapping("/update/attendance/{ticketId}")
    public ResponseEntity updateTicketAttendance(@PathVariable int ticketId) {
        // Update a ticket by sending Ticket instance
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
