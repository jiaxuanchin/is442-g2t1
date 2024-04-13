package com.is442g2t1.ticketbookingsystem.booking;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442g2t1.ticketbookingsystem.security.jwt.JWTService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"})
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    private JWTService jwtGenerator;

    @Autowired
    public BookingController(BookingService bookingService, JWTService jwtGenerator) {
        this.bookingService = bookingService;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity getAllBookings() {

        ResponseEntity result = this.bookingService.getAllBookings();
        return result;
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity getOneBooking(@PathVariable int bookingId) {

        ResponseEntity result = this.bookingService.getOneBooking(bookingId);
        return result;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUserBooking(@PathVariable int userId) {

        ResponseEntity result = this.bookingService.getUserBooking(userId);
        return result;
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity getEventBooking(@PathVariable int eventId) {

        ResponseEntity result = this.bookingService.getEventBooking(eventId);
        return result;
    }

    @PostMapping("/new/{payType}")
    // @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity createBooking(HttpServletRequest request, @RequestBody Booking booking, @PathVariable String payType) { 
        
        // String payType = "stripe";
        String token = jwtGenerator.extractJwtFromRequest(request);

        ResponseEntity result = this.bookingService.createBooking(token, booking, payType);
        return result;
    }

    @DeleteMapping("/delete/{bookingId}")
    @PreAuthorize("hasAuthority('event_manager')")
    public ResponseEntity cancelBooking(@PathVariable int bookingId) {

        ResponseEntity result = this.bookingService.cancelBooking(bookingId);
        return result;
    }

    @PostMapping("/check")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity checkIfCanBook(@RequestBody Booking booking) {

        ResponseEntity result = this.bookingService.checkIfCanBook(booking);
        return result;
    }

}