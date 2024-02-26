package com.is442g2t1.ticketbookingsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.service.*; 

@RestController
public class TicketingOfficerController {

    @Autowired
    private TicketingOfficerService ticketingOfficerService;

    // Endpoint to verify ticket validity
    @GetMapping("/verifyTicket")
    public ResponseEntity<String> verifyTicket(@RequestParam String ticketId) {
        boolean isValid = ticketingOfficerService.verifyTicket(ticketId);
        if (isValid) {
            return ResponseEntity.ok("Ticket is valid");
        } else {
            return ResponseEntity.badRequest().body("Ticket is invalid or does not exist");
        }
    }

    // Endpoint for processing on-site ticket sales
    @PostMapping("/processOnSiteSale")
    public ResponseEntity<String> processOnSiteSale(@RequestParam String eventId, @RequestParam int numOfTickets) {
        try {
            ticketingOfficerService.processOnSiteSale(eventId, numOfTickets);
            return ResponseEntity.ok("On-site ticket sale processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not process on-site ticket sale: " + e.getMessage());
        }
    }

    // Endpoint for issuing e-tickets
    @PostMapping("/issueETicket")
    public ResponseEntity<String> issueETicket(@RequestParam String customerId, @RequestParam String eventId, @RequestParam int numOfTickets) {
        try {
            // Correcting the service call to match expected return type
            Ticket newTicket = ticketingOfficerService.issueETicket(customerId, eventId, numOfTickets);
            // Extracting ticket ID from the returned Ticket object
            String eTicketId = newTicket.getTicketId();
            return ResponseEntity.ok("E-Ticket issued successfully. Ticket ID: " + eTicketId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not issue e-ticket: " + e.getMessage());
        }
    }
    

}

