package com.is442g2t1.ticketbookingsystem.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {

        List<Booking> result = this.bookingService.getAllBookings();
        
        return result;
    }

    @GetMapping("/{bookingId}")
    public Optional<Booking> getOneBooking(@PathVariable int bookingId) {

        Optional<Booking> result = this.bookingService.getOneBooking(bookingId);
        return result;
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBooking(@PathVariable int userId) {

        List<Booking> result = this.bookingService.getUserBooking(userId);
        return result;
    }

    @GetMapping("/event/{eventId}")
    public List<Booking> getEventBooking(@PathVariable int eventId) {

        List<Booking> result = this.bookingService.getEventBooking(eventId);
        return result;
    }

    @PostMapping("/new")
    public String createBooking(@RequestBody Booking booking) {
        String result = this.bookingService.createBooking(booking);
        return result;
    }

    @DeleteMapping("/delete/{bookingId}")
    public String cancelBooking(@PathVariable int bookingId) {

        String result = this.bookingService.cancelBooking(bookingId);
        return result;
    }

}
