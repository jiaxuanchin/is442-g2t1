package com.is442g2t1.ticketbookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.is442g2t1.ticketbookingsystem.model.Booking;
import com.is442g2t1.ticketbookingsystem.repository.BookingRepository;

public class BookingService {

    private BookingRepository bookingRepository;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
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
    
    public String getUserBooking() {
        return "";
    }

    public String createBooking(Booking booking) {
        try {
            System.out.println("Booking:" + booking);
            bookingRepository.save(booking);

            // -------------------- TO BE DONEEEEE --------------------
            purchaseTicket(booking.getBookingId(), booking.getNumOfTickets());

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

    // -------------------- TO BE DONEEEEE --------------------
    public String cancelBooking(int bookingId) {
        try {
            System.out.println("Cancelling booking...");


        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
        return "";
    }

    // -------------------- TO BE DONEEEEE --------------------
    public void purchaseTicket(int bookingId, int numOfTickets) {
        System.out.println("Purchasing ticket for bookingId: " + bookingId + "...");
        // return "";
    }

}
