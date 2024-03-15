package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketingOfficer")
public class TicketingOfficerController {

    private final TicketingOfficerService ticketingOfficerService;

    @Autowired
    public TicketingOfficerController(TicketingOfficerService ticketingOfficerService) {
        this.ticketingOfficerService = ticketingOfficerService;
    }

    @GetMapping("/verifyTicket/{ticketId}")
    public ResponseEntity<?> verifyTicketValidity(@PathVariable int ticketId) {
        return ticketingOfficerService.verifyTicketValidity(ticketId);
    }

    @PostMapping("/processOnsiteSales")
    public ResponseEntity<?> processOnsiteTicketSales(@RequestParam int eventId, 
                                                      @RequestParam int numOfTickets, 
                                                      @RequestParam int customerId) {
        return ticketingOfficerService.processOnsiteTicketSales(eventId, numOfTickets, customerId);
    }

    @PostMapping("/issueETicket")
    public ResponseEntity<?> issueETicket(@RequestParam int bookingId, @RequestParam String customerEmail) {
        return ticketingOfficerService.issueETicket(bookingId, customerEmail);
    }
}
