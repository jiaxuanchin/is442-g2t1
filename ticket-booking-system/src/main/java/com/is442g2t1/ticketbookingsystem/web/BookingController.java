package com.is442g2t1.ticketbookingsystem.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.model.*;
import com.is442g2t1.ticketbookingsystem.service.*;
import com.is442g2t1.ticketbookingsystem.repository.*;

import io.micrometer.common.lang.NonNull;


@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingRepository bookingRepository) {
        this.bookingService = new BookingService
        (bookingRepository);
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        // Get all booking
        List<Booking> result = this.bookingService.getAllBookings();
        
        return result;
    }

    @GetMapping("/{bookingId}")
    public Optional<Booking> getOneBooking(@PathVariable int bookingId) {
        // Get all booking
        Optional<Booking> result = this.bookingService.getOneBooking(bookingId);
        return result;
    }

    @GetMapping("/user/{userId}")
    public String getUserBooking(int userId) {
        // Get all booking
        return "success";
    }

    @PostMapping("/new")
    public String createBooking(@RequestBody Booking booking) {
        String result = this.bookingService.createBooking(booking);
        return result;
    }

    @PutMapping("/update")
    public void updateBooking() {
        // Update booking

    }

}
