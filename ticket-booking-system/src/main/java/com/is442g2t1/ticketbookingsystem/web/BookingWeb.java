package com.is442g2t1.ticketbookingsystem.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingWeb {
    
    @PostMapping("/booking")
    public void createBooking() {
        // Create booking
    }

    @PutMapping("/booking")
    public void updateBooking() {
        // Update booking
    }
}
