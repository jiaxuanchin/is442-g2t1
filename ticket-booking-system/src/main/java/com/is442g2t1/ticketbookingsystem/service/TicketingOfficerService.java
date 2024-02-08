package com.is442g2t1.ticketbookingsystem.service;

@Service
public class TicketingOfficerService {

    @Autowired
    private TicketingOfficerRepository ticketingOfficerRepository;

    public boolean validateTicket(Ticket ticket) {
        // Implement validation 
        return true;
    }

    public void sellTicket(TicketingOfficer ticketingOfficer, Ticket ticket) {
        // Implement ticket sales 
    }

}