package com.is442g2t1.ticketbookingsystem.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.model.Booking;
import com.is442g2t1.ticketbookingsystem.repository.BookingRepository;


@RestController
public class BookingController {

    private BookingRepository bookingRepository;

    @PostMapping("/booking")
    public String createBooking(@RequestBody Booking booking) {
        // Create booking

        // Get user data from request body
        System.out.println("Booking:" + booking);

        bookingRepository.save(booking);
        
        return "";
    }

    @PutMapping("/booking")
    public void updateBooking() {
        // Update booking

    }
}
