package com.is442g2t1.ticketbookingsystem.ticket;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.is442g2t1.ticketbookingsystem.booking.Booking;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ResponseEntity getAllTickets() {
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            System.out.println("Tickets retrieved");
            if (tickets.isEmpty()) {
                return ResponseEntity.status(404).body("No tickets found");
            }

            System.out.println(tickets.toString());
            return ResponseEntity.ok(tickets);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity getTicketById(int ticketId) {
        try {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            if (!ticket.isPresent()) {
                return ResponseEntity.status(404).body("Ticket not found");
            }
            System.out.println(ticket.toString());
            return ResponseEntity.ok(ticket);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }
    }

    public ResponseEntity getTicketsByBookingId(int bookingId) {
        try {
            Booking booking = new Booking(bookingId);
            List<Ticket> tickets = ticketRepository.findTicketsByBooking(booking);

            if (tickets.isEmpty()) {
                return ResponseEntity.status(404).body("Ticket not found");
            }
            // System.out.println(tickets.toString());
            return ResponseEntity.ok(tickets);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity createTicket(Ticket ticket) {
        try {
            Ticket createdTicket = ticketRepository.save(ticket);
            System.out.println("CREATED TICKET: " + createdTicket.toString());

            return ResponseEntity.ok("Ticket created");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating ticket");

        }
    }

    public ResponseEntity updateTicketAttendance(int ticketId) {
        try {
            Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
            if (!ticketOptional.isPresent()) {
                return ResponseEntity.status(404).body("Ticket not found");
            }
            Ticket ticket = ticketOptional.get();
            ticket.setAttendance(true);
            Ticket newTicket = ticketRepository.save(ticket);
            System.out.println(newTicket.toString());
            return ResponseEntity.ok(newTicket);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating ticket with id: " + ticketId);
        }
    }

    // ------------- try deleting ticket, not necessary but to make sure deletion works without cascade removal from booking
    // public String deleteTicket(int ticketId) {
    //     try {
    //         Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
    //         if (!ticketOptional.isPresent()) {
    //             throw new RuntimeException("Ticket not found");
    //         }
    //         Ticket ticket = ticketOptional.get();
    //         ticketRepository.delete(ticket);
    //         System.out.println("Ticket deleted");
    //         return """
    //             {
    //                 "status": 200,
    //                 "message": "Ticket deleted"
    //             }
    //             """;

    //     } catch (Exception e) {
    //         System.err.println("Error: " + e.getMessage());
    //         throw e;
    //     }
    // }
    
}
