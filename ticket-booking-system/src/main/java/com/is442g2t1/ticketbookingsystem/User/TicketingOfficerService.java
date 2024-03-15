package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.event.Event;
import com.is442g2t1.ticketbookingsystem.event.EventService;
import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import com.is442g2t1.ticketbookingsystem.booking.Booking;
import com.is442g2t1.ticketbookingsystem.booking.BookingService;
import com.is442g2t1.ticketbookingsystem.ticket.TicketService;
import com.is442g2t1.ticketbookingsystem.email.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;

@Service
public class TicketingOfficerService {

    private final BookingService bookingService;
    private final EventService eventService;
    private final EmailService emailService;
    private final TicketService ticketService;
    

    @Autowired
    public TicketingOfficerService(BookingService bookingService, EventService eventService, EmailService emailService, TicketService ticketService) {
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.emailService = emailService;
        this.ticketService = ticketService;
    }

    
    //****** need to check whether the booking is cancelled?
    //****** need to check whether the ticket that belongs to that booking is already used?
    //*****  update attendance in Event (getFilled) or Ticket(updateAttendance) ?

    public ResponseEntity<?> verifyTicketValidity(int ticketId) {

        //check booking for a specific ticket
        ResponseEntity<?> bookingResponse = bookingService.getOneBooking(ticketId);
        if (!bookingResponse.getStatusCode().is2xxSuccessful()) {
            return bookingResponse;
        }

        Booking booking = (Booking) bookingResponse.getBody();
        if (booking == null) {
            return ResponseEntity.status(404).body("Booking not found");
        }

        //fetch eventID using booking
        ResponseEntity<?> eventResponse = eventService.searchById(booking.getEventId());
        if (!eventResponse.getStatusCode().is2xxSuccessful()) {
            return eventResponse;
        }

        Event event = (Event) eventResponse.getBody();
        if (event == null) {
            return ResponseEntity.status(404).body("Event not found for booking");
        }

        // check whether event is valid for today
        boolean isEventForToday = event.getEventDate().isEqual(LocalDate.now());

        if (isEventForToday) {
            // Update the ticket's attendance status
            ResponseEntity<?> updateResponse = ticketService.updateTicketAttendance(ticketId);
            if (!updateResponse.getStatusCode().is2xxSuccessful()) {
                return updateResponse; // If ticket update fails
            }
            return ResponseEntity.ok("Ticket is valid and for today's event. Attendance has been updated.");
        } 
        else {
            return ResponseEntity.ok("Ticket is not for today's event.");
        }
        
    }

    public ResponseEntity<?> processOnsiteTicketSales(int eventId, int numOfTickets, int customerId) {
        // Validate the event and check ticket availability
        ResponseEntity<?> eventResponse = eventService.searchById(eventId);
        if (!eventResponse.getStatusCode().is2xxSuccessful()) {
            return eventResponse;
        }
    
        Event event = (Event) eventResponse.getBody();
        if (event == null || event.getFilled() < numOfTickets) {
            return ResponseEntity.badRequest().body("Event not found or not enough tickets available");
        }
    
        // updating event details.
        EventCreateDTO eventCreateDTO = new EventCreateDTO();
        eventCreateDTO.setEventTitle(event.getEventTitle());
        eventCreateDTO.setEventDesc(event.getEventDesc());
        eventCreateDTO.setEventLoc(event.getEventLoc());
        eventCreateDTO.setEventDate(event.getEventDate());
        eventCreateDTO.setStartTime(event.getStartTime());
        eventCreateDTO.setEndTime(event.getEndTime());
        eventCreateDTO.setCapacity(event.getCapacity());
        eventCreateDTO.setTicketPrice(event.getTicketPrice());
        eventCreateDTO.setCancelFee(event.getCancelFee());
        eventCreateDTO.setFilled(event.getFilled() + numOfTickets); 
    
        // Update the event using the event service.
        ResponseEntity<?> updateResponse = eventService.editEvent(eventCreateDTO, eventId);
        if (!updateResponse.getStatusCode().is2xxSuccessful()) {
            return updateResponse; 
        }
    
        // Now, create a new booking for the onsite ticket sales.
        Booking booking = new Booking();
        booking.setEventId(eventId);
        booking.setNumOfTickets(numOfTickets);
        booking.setUserId(customerId); 
    
        // Call the booking service to create the booking.
        ResponseEntity<?> bookingResponse = bookingService.createBooking(booking);
        if (!bookingResponse.getStatusCode().is2xxSuccessful()) {
            return bookingResponse;
        }
    
        // Return successful response
        return ResponseEntity.ok().body("Onsite ticket sales processed successfully.");
    }

    public ResponseEntity<?> issueETicket(int bookingId, String customerEmail) {

        // Fetch booking details
        ResponseEntity<?> bookingResponse = bookingService.getOneBooking(bookingId);
        if (!bookingResponse.getStatusCode().is2xxSuccessful()) {
            return bookingResponse; 
        }

        Booking booking = (Booking) bookingResponse.getBody();
        if (booking == null) {
            return ResponseEntity.status(404).body("Booking not found");
        }

        // Generate e-ticket information
        String eTicketInfo = "E-Ticket for Booking ID: " + bookingId + "\n" +
                             "Event ID: " + booking.getEventId() + "\n" +
                             "Number of Tickets: " + booking.getNumOfTickets() + "\n" +
                             "Date of Issue: " + java.time.LocalDate.now();

        // Send e-ticket information via email
        emailService.sendEmail(customerEmail, "Your E-Ticket", eTicketInfo);

        // Return successful response
        return ResponseEntity.ok("E-Ticket issued successfully to " + customerEmail);
    }
    
}
