package com.is442g2t1.ticketbookingsystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.model.*;
import com.is442g2t1.ticketbookingsystem.service.*;
import com.is442g2t1.ticketbookingsystem.repository.*;

import io.micrometer.common.lang.NonNull;


@RestController
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingRepository bookingRepository) {
        this.bookingService = new BookingService
        (bookingRepository);
    }

    @GetMapping("/allbookings")
    public String getAllBookings() {
        // Get all booking
        String result = this.bookingService.getAllBookings();
        
        return result;
    }

    @GetMapping("/booking")
    public String getOneBooking() {
        // Get all booking
        return "success";
    }

    @GetMapping("/booking/{userId}")
    public String getUserBooking(int userId) {
        // Get all booking
        return "success";
    }

    @PostMapping("/booking")
    public String createBooking(@NonNull @RequestBody Booking booking) {
        String result = this.bookingService.createBooking(booking);
        return result;
    }

    @PutMapping("/booking")
    public void updateBooking() {
        // Update booking

    }

}
