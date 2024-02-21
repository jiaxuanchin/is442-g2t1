package com.is442g2t1.ticketbookingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.is442g2t1.ticketbookingsystem.model.Ticket;
import com.is442g2t1.ticketbookingsystem.repository.TicketRepository;

public class TicketService {
    
    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public String findTicketById(int ticketId) {
        try {
            String ticket = ticketRepository.findById(ticketId).toString();
            System.out.println(ticket.toString());
            return """
                {
                "status": 200,
                "data": """ 
                    + ticket +
                """
                \n} """;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return """
                {
                    "status": 500,
                    "data": {},
                    "message": "Error retrieving ticket"
                }
                """;
        }
    }

    public String getAllTickets() {
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            System.out.println("Tickets retrieved");
            // for (Ticket ticket : tickets) {
            //     System.out.println(ticket.toString());
            // }

            System.out.println(tickets.toString());
            return """
                {
                    "status": 200,
                    "data": """
                    + tickets.toString() +
                            """
                }
                """;
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return """
                {
                    "status": 500,
                    "message": "Error retrieving tickets"
                \n}
                """;
        }
    }

    public String createTicket(Ticket ticket) {
        try {
            ticketRepository.save(ticket);
            System.out.println("Ticket created");
            return """
                {
                    "status": 200,
                    "message": "Ticket created"
                }
                """;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return """
                {
                    "status": 500,
                    "message": "Error creating ticket"
                }
                """;
        }
    }
}
