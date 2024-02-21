package com.is442g2t1.ticketbookingsystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.model.*;
import com.is442g2t1.ticketbookingsystem.service.*;
import com.is442g2t1.ticketbookingsystem.repository.*;

@RestController
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketService = new TicketService(ticketRepository);
    }

    @GetMapping("/alltickets")
    public String getAllTickets() {
        // Get all tickets
        String result = this.ticketService.getAllTickets();
        
        return result;
    }

    @GetMapping("/ticket/{ticketId}")
    public String getTicketById(@PathVariable int ticketId) {
        // Get all tickets
        String result = this.ticketService.findTicketById(ticketId);

        // If ticket does not exist: {"data":Optional.empty} is returned
        return result;
    }

    @PostMapping("/ticket")
    public String createTicket(@RequestBody Ticket ticket) {
        // Create a ticket
        String result = this.ticketService.createTicket(ticket);
        return result;
    }
    
}
