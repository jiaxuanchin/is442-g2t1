package com.is442g2t1.ticketbookingsystem.booking;

import com.is442g2t1.ticketbookingsystem.ticket.Ticket;
import com.is442g2t1.ticketbookingsystem.ticket.TicketRepository;
import com.is442g2t1.ticketbookingsystem.ticket.TicketService;
import com.is442g2t1.response.StatusResponse;
import com.is442g2t1.ticketbookingsystem.User.Customer;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;
import com.is442g2t1.ticketbookingsystem.User.UserRepository;
import com.is442g2t1.ticketbookingsystem.event.Event;
import com.is442g2t1.ticketbookingsystem.event.EventRepository;
import com.is442g2t1.ticketbookingsystem.email.EmailService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private TicketRepository ticketRepository;
    private TicketService ticketService;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private EmailService emailService;
    private WebClient webClient;

    @Autowired
    public BookingService(BookingRepository bookingRepository, TicketRepository ticketRepository,
            TicketService ticketService, UserRepository userRepository, EventRepository eventRepository,
            EmailService emailService, WebClient webClient) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.emailService = emailService;
        this.webClient = webClient;
    }

    public ResponseEntity getAllBookings() {
        try {
            System.out.println(this.bookingRepository.findAll());

            List<Booking> bookings = this.bookingRepository.findAll();
            return ResponseEntity.ok(bookings);

        } catch (Exception e) {
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

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity getUserBooking(int userId) {
        try {
            List<Booking> booking = this.bookingRepository.findByUserId(userId);
            if (booking.isEmpty()) {
                return ResponseEntity.status(404).body("Booking not found");
            }
            return ResponseEntity.ok(booking);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity getEventBooking(int eventId) {
        try {
            List<Booking> booking = this.bookingRepository.findByEventId(eventId);
            return ResponseEntity.ok(booking);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity checkIfCanBook(Booking booking) {
        // Fetch the event associated with the booking
        int eventId = booking.getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

        // Check if the event capacity is full
        if ((booking.getNumOfTickets() + event.getFilled()) > event.getCapacity()) {
            int remainingNum = event.getCapacity() - event.getFilled();
            String message = "Event capacity is full. Remaining number of tickets: " + remainingNum;
            return ResponseEntity.status(400)
                    .body(Map.of("error", message));
        }
        return ResponseEntity.ok(true);
    }

    public ResponseEntity createBooking(Booking booking, String payType) {
        try {
            System.out.println("Creating booking:" + booking);

            int userId = booking.getUserId();
            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }
            UserEntity user = userOptional.get();

            // Check if the user is a customer and has enough balance to purchase tickets
            if (!(user instanceof Customer)) {
                return ResponseEntity.status(400).body("User is not a customer");
            }
            Customer customer = (Customer) user;

            // Fetch the event associated with the booking
            int eventId = booking.getEventId();
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

            // Check if the event capacity is full
            // if (booking.getNumOfTickets() + event.getFilled() > event.getCapacity()) {
            //     return ResponseEntity.status(400).body("Event capacity is full");
            // }

            // Calculate the new filled capacity
            int newFilledCapacity = event.getFilled() + booking.getNumOfTickets();

            // Update the event filled capacity
            event.setFilled(newFilledCapacity);
            eventRepository.save(event);

            // if (payType.equals("ewallet")) {
            //     customer.reduceBalance(totalTicketPrice);
            //     userRepository.save(user);
            // }

            bookingRepository.save(booking);

            ResponseEntity result = purchaseTicket(booking);
            System.out.println("RESULT CODE: " + result.getStatusCode());
            System.out.println("CHECK RESULTS: " + result.getStatusCode().equals(HttpStatus.OK));
            if (!result.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(404).body("Error creating booking");
            }

            double totalTicketPrice = calculateTotalTicketPrice(booking);
            if (payType.equals("ewallet")) {
                if (totalTicketPrice > customer.getBalance()) {
                    return ResponseEntity.status(400).body("Insufficient balance to purchase tickets");
                } else {
                    // Deduct the ticket price from the customer's balance
                    customer.reduceBalance(totalTicketPrice);
                    userRepository.save(user);
                }
            }

            String bookingId = Integer.toString(booking.getBookingId());
            String subject = "Event Booking Confirmation (Booking ID: " + bookingId + ")";

            // Include the user's name in the email body
            String body = "Dear " + customer.getUser_fname() + ",\n\n"
                    + "Thank you for your booking. Your tickets have been successfully purchased for " + '"'
                    + event.getEventDesc() + '"' + " " + event.getEventTitle() + ".\n\n"
                    + "Date (YYYY-MM-DD): " + event.getEventDate().toString() + "\n"
                    + "Location: " + event.getEventLoc() + "\n"
                    + "Time: " + event.getStartTime() + " - " + event.getEndTime() + "\n"
                    + "Number of Tickets Bought: " + booking.getNumOfTickets() + "\n"
                    + "Total Amount Paid: $" + totalTicketPrice + "0\n\n"
                    + "Please log in to our web app to view your tickets. For any further assistance, please contact us by replying to this email.\n\n"
                    + "Brought to you by,\n"
                    + "G2T1 Event Management Team";

            emailService.sendEmail(customer.getEmail(), subject, body);

            return ResponseEntity.ok("Booking created");

        } catch (Exception e) {
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

            int userId = booking.getUserId();
            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }
            UserEntity user = userOptional.get();

            // Check if the user is a customer and has enough balance to purchase tickets
            if (!(user instanceof Customer)) {
                return ResponseEntity.status(400).body("User is not a customer");
            }
            Customer customer = (Customer) user;
            double totalTicketPrice = calculateTotalTicketPrice(booking);

            // Fetch the event associated with the booking
            int eventId = booking.getEventId();
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

            // Calculate the new filled capacity
            int newFilledCapacity = event.getFilled() - booking.getNumOfTickets();

            // Update the event filled capacity
            event.setFilled(newFilledCapacity);
            eventRepository.save(event);

            // Add the ticket price to the customer's balance
            customer.increaseBalance(totalTicketPrice - event.getCancelFee());
            userRepository.save(user);

            String subject = "Event Booking Cancellation (Booking ID: " + bookingId + ")";

            // Include the user's name in the email body
            String body = "Dear " + user.getUser_fname() + ",\n\n"
                    + "We have received your booking cancellation. Your tickets have been successfully refunded for "
                    + '"' + event.getEventDesc() + '"' + " " + event.getEventTitle() + ".\n\n"
                    + "Date (YYYY-MM-DD): " + event.getEventDate().toString() + "\n"
                    + "Location: " + event.getEventLoc() + "\n"
                    + "Time: " + event.getStartTime() + " - " + event.getEndTime() + "\n"
                    + "Number of Tickets Refunded: " + booking.getNumOfTickets() + "\n"
                    + "Total Amount To Be Refunded: $" + (totalTicketPrice - event.getCancelFee()) + "0\n\n"
                    + "Please log in to our web app to check the refund into your e-wallet. For any further assistance, please contact us by replying to this email.\n\n"
                    + "Brought to you by,\n"
                    + "G2T1 Event Management Team";

            emailService.sendEmail(user.getEmail(), subject, body);

            bookingRepository.delete(booking);

            return ResponseEntity.ok("Booking cancelled");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity cancelAllBookingsUnderEvent(int eventId) {
        try {
            /// Retrieve all bookings associated with the event ID
            List<Booking> bookings = bookingRepository.findByEventId(eventId);

            // Check if there are any bookings
            if (bookings.isEmpty()) {
                return ResponseEntity.status(400).body("No bookings found for event with ID: " + eventId);
            }

            // Iterate over each booking and cancel it
            for (Booking booking : bookings) {
                // Calculate refund amount
                double refundAmount = calculateTotalTicketPrice(booking);

                // Retrieve the user by ID
                Optional<UserEntity> userOptional = userRepository.findById(booking.getUserId());
                if (userOptional.isPresent()) {
                    UserEntity user = userOptional.get();

                    // Check if the user is a customer
                    if (user instanceof Customer) {
                        Customer customer = (Customer) user;

                        // Increase balance with refund amount
                        customer.increaseBalance(refundAmount);

                        // Save the updated user entity
                        userRepository.save(customer);
                    }
                }
                bookingRepository.delete(booking);
                sendMassCancellationEmail(booking);
            }

            return ResponseEntity.ok("All bookings for the event have been cancelled successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity purchaseTicket(Booking booking) {
        System.out.println("Purchasing ticket for bookingId: " + booking.getBookingId() + "...");

        try {
            for (int i = 0; i < booking.getNumOfTickets(); i++) {
                // Create a new Ticket object for each iteration
                Ticket ticket = new Ticket(booking);

                // Save the ticket
                ResponseEntity ticketResponse = ticketService.createTicket(ticket);

                if (!ticketResponse.getStatusCode().equals(HttpStatus.OK)) {
                    return ResponseEntity.status(500).body("Error purchasing ticket");
                }
            }
            return ResponseEntity.ok("Tickets purchased successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    private double calculateTotalTicketPrice(Booking booking) {
        double totalTicketPrice = 0.0;
        try {
            // Retrieve the event associated with the booking
            Optional<Event> eventOptional = eventRepository.findById(booking.getEventId());
            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();

                // Calculate the total ticket price based on the ticket price and number of tickets
                double ticketPrice = event.getTicketPrice();
                int numOfTickets = booking.getNumOfTickets();
                totalTicketPrice = ticketPrice * numOfTickets;
            } else {
                throw new IllegalArgumentException("Event not found for booking: " + booking.getBookingId());
            }
        } catch (Exception e) {
            // Handle any exceptions or errors here
            // For simplicity, we'll just print the error message
            System.err.println("Error calculating total ticket price: " + e.getMessage());
        }
        return totalTicketPrice;
    }

    private void sendMassCancellationEmail(Booking booking) {
        try {
            // Fetch user details associated with the booking
            int userId = booking.getUserId();
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            String email = user.getEmail();
            String name = user.getUser_fname();

            // Fetch the event associated with the booking
            int eventId = booking.getEventId();
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

            // Construct email subject and body
            String subject = "Event Booking Cancellation (Booking ID: " + booking.getBookingId() + ")";
            String body = "Dear " + name + ",\n\n"
                    + "We regret to inform you that the event " + '"' + event.getEventDesc() + '"' + " "
                    + event.getEventTitle() + " has been cancelled.\n\n"
                    + "Refunds have already been credited into your e-wallet. For any further assistance, please contact us by replying to this email.\n\n"
                    + "Brought to you by,\n"
                    + "G2T1 Event Management Team";

            // Send cancellation email
            emailService.sendEmail(email, subject, body);
        } catch (Exception e) {
            System.err.println("Error sending cancellation email: " + e.getMessage());
        }
    }

}