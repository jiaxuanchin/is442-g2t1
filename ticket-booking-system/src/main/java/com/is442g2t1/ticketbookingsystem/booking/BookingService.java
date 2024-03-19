package com.is442g2t1.ticketbookingsystem.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookingService {

    private BookingRepository bookingRepository;

    private WebClient webClient;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository, WebClient webClient) {
        this.bookingRepository = bookingRepository;
        this.webClient = webClient;
    }

    public ResponseEntity getAllBookings() {
        try {
            System.out.println(this.bookingRepository.findAll());
            
            List<Booking> bookings = this.bookingRepository.findAll();
            return ResponseEntity.ok(bookings);

        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity getOneBooking(int bookingId) {
        try {
            Optional<Booking> booking = this.bookingRepository.findById(bookingId);
            if (!booking.isPresent()) {
                return ResponseEntity.status(404).body("Booking not found");
            }
            return ResponseEntity.ok(booking);
            
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
    public ResponseEntity getUserBooking(int userId) {
        try {
            List<Booking> booking = this.bookingRepository.findByUserId(userId);
            if (booking.equals(booking)) {
                return ResponseEntity.status(404).body("Booking not found");
            }
            return ResponseEntity.ok(booking);

        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity getEventBooking(int eventId) {
        try {
            List<Booking> booking = this.bookingRepository.findByEventId(eventId);
            return ResponseEntity.ok(booking);

        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    } 

    public ResponseEntity createBooking(Booking booking) {
        try {
            System.out.println("Create booking:" + booking);
            bookingRepository.save(booking);

            ResponseEntity result = purchaseTicket(booking);
            // need to update event service? (TBD AFTER MERGE)
            // need to trigger emailer (TBD AFTER MERGE)
            System.out.println("RESULT CODE: " + result.getStatusCode());
            System.out.println("CHECK RESULTS: " + result.getStatusCode().equals(HttpStatus.OK));
            if (!result.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(404).body("Error creating booking");
            }

            return ResponseEntity.ok("Booking created");

        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity cancelBooking(int bookingId) {
        try {
            System.out.println("Cancelling booking...");

            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
            if (!bookingOptional.isPresent()) {
                return ResponseEntity.status(404).body("Booking not found");
            } 
            Booking booking = bookingOptional.get();
            bookingRepository.delete(booking);
            return ResponseEntity.ok("Booking cancelled");

        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());

        }
    }

    public ResponseEntity purchaseTicket(Booking booking) {
        System.out.println("Purchasing ticket for bookingId: " + booking.getBookingId() + "...");

        ResponseEntity<String> ticketResponse = null;

        try {
            for (int i = 0; i < booking.getNumOfTickets(); i++) {

                ticketResponse = webClient.post()
                .uri("/ticket/new")
                .bodyValue(booking)
                .retrieve()
                .toEntity(String.class)
                .block();

            }
            return ticketResponse;

        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
