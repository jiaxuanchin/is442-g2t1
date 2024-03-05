package com.is442g2t1.ticketbookingsystem.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g2t1.ticketbookingsystem.ticket.TicketService;

@Service
public class BookingService {

    private BookingRepository bookingRepository;

    private TicketService ticketService;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository, TicketService ticketService) {
        this.bookingRepository = bookingRepository;
        this.ticketService = ticketService;
    }

    public List<Booking> getAllBookings() {
        try {
            System.out.println(this.bookingRepository.findAll());
            
            List<Booking> bookings = this.bookingRepository.findAll();
            return bookings;

        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public Optional<Booking> getOneBooking(int bookingId) {
        try {
            Optional<Booking> booking = this.bookingRepository.findById(bookingId);
            return booking;
            
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    public List<Booking> getUserBooking(int userId) {
        try {
            List<Booking> booking = this.bookingRepository.findByUserId(userId);
            return booking;

        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public List<Booking> getEventBooking(int eventId) {
        try {
            List<Booking> booking = this.bookingRepository.findByEventId(eventId);
            return booking;

        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    } 

    public String createBooking(Booking booking) {
        try {
            System.out.println("Create booking:" + booking);
            bookingRepository.save(booking);

            boolean result = purchaseTicket(booking.getBookingId(), booking.getNumOfTickets());
            // need to update event service? (TBD AFTER MERGE)
            // need to trigger emailer (TBD AFTER MERGE)

            if (!result) {
                return """
                    {
                        "status": 500,
                        "message": "Error creating booking"
                    }
                    """;
            }

            return """
                {
                    "status": 200,
                    "message": "Booking created"
                }
                """;

        } catch(Exception e) {
            System.out.println("Error: " + e);
            return """
                {
                    "status": 500,
                    "message": "Error creating booking"
                }
                """;
        }
    }

    public String cancelBooking(int bookingId) {
        try {
            System.out.println("Cancelling booking...");
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
            if (!bookingOptional.isPresent()) {
                return """
                    {
                        "status": 404,
                        "message": "Booking not found"
                    }
                    """;
            } 
            Booking booking = bookingOptional.get();
            bookingRepository.delete(booking);
            return """
                {
                    "status": 200,
                    "message": "Booking cancelled"
                }
                """;

        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;

        }
    }

    public Boolean purchaseTicket(int bookingId, int numOfTickets) {
        System.out.println("Purchasing ticket for bookingId: " + bookingId + "...");

        try {
            for (int i = 0; i < numOfTickets; i++) {
                Booking booking = new Booking(bookingId);
                this.ticketService.createTicket(booking);
            }
            return true;

        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

}
