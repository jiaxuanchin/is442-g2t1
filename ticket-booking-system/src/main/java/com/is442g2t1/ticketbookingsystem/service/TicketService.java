package com.is442g2t1.ticketbookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.is442g2t1.ticketbookingsystem.model.*;
import com.is442g2t1.ticketbookingsystem.repository.TicketRepository;

public class TicketService {
    
    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            System.out.println("Tickets retrieved");
            // for (Ticket ticket : tickets) {
            //     System.out.println(ticket.toString());
            // }

            System.out.println(tickets.toString());
            return tickets;
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public Optional<Ticket> getTicketById(int ticketId) {
        try {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            if (!ticket.isPresent()) {
                throw new RuntimeException("Ticket not found");
            }
            System.out.println(ticket.toString());
            return ticket;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Ticket> getTicketsByBookingId(int bookingId) {
        try {
            Booking booking = new Booking(bookingId);
            List<Ticket> tickets = ticketRepository.findTicketsByBooking(booking);
            System.out.println(tickets.toString());
            return tickets;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public String createTicket(Booking booking) {
        try {
            // only need to send bookingId from BookingService class
            Ticket ticket = new Ticket(booking);
            System.out.println("Ticket: " + ticket.toString());
            Ticket createdTicket = ticketRepository.save(ticket);
            System.out.println(createdTicket.toString());
            return """
                {
                    "status": 200,
                    "message": "Ticket created",
                    "ticket": """
                    + createdTicket.toString() +
                            """
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

    public Ticket updateTicketAttendance(int ticketId) {
        try {
            Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
            if (!ticketOptional.isPresent()) {
                throw new RuntimeException("Ticket not found");
            }
            Ticket ticket = ticketOptional.get();
            ticket.setAttendance(true);
            Ticket newTicket = ticketRepository.save(ticket);
            System.out.println(newTicket.toString());
            return ticket;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
}
