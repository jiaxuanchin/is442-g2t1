package com.is442g2t1.ticketbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.is442g2t1.ticketbookingsystem.model.Booking;
import com.is442g2t1.ticketbookingsystem.repository.BookingRepository;

public class BookingService {

    private BookingRepository bookingRepository;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String getAllBookings() {
        try {
            System.out.println(this.bookingRepository.findAll());
            return "success";
        } catch(Exception e) {
            return "Error: " + e;
        }
    }
    
    public String getUserBooking() {
        return "";
    }

    public String updateBooking() {
        return "";
    }

    public String cancelBooking() {
        return "";
    }

    public String createBooking(Booking booking) {
        try {
            System.out.println("Booking:" + booking);
            bookingRepository.save(booking);
            return "{ statusCode: 200, headers: { 'content-type': 'application/json' }, body: { 'message': 'booking created successfully' }";

        } catch(Exception e) {
            System.out.println("Error: " + e);
            return "{ statusCode: 500, headers: { 'content-type': 'application/json' }, body: { 'message': 'unable to create booking' }";
        }
    }

    public String purchaseTicket() {
        return "";
    }

    public String cancelTicket() {
        return "";
    }
}
