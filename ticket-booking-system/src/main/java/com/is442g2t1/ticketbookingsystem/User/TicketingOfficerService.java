package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.event.Event;
import com.is442g2t1.ticketbookingsystem.event.EventService;
import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import com.is442g2t1.ticketbookingsystem.booking.Booking;
import com.is442g2t1.ticketbookingsystem.booking.BookingService;
import com.is442g2t1.ticketbookingsystem.ticket.Ticket;
import com.is442g2t1.ticketbookingsystem.ticket.TicketService;
import com.rabbitmq.client.RpcClient.Response;
import com.is442g2t1.ticketbookingsystem.email.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;

@Service
public class TicketingOfficerService {


    private final RestTemplate restTemplate;
    private final String eventBaseUrl = "http://localhost:8080/event/";
    private final String bookingBaseUrl = "http://localhost:8080/booking/";
    private final String ticketBaseUrl = "http://localhost:8080/ticket/";
    private final String emailBaseUrl = "http://localhost:8080/email/";

    

    @Autowired
    public TicketingOfficerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }


    public ResponseEntity<?> verifyTicketValidity(int ticketId) {

        //check booking for a specific ticket
        ResponseEntity<Booking> bookingResponse = restTemplate.getForEntity(bookingBaseUrl + "/getOneBooking/" + ticketId, Booking.class);
        if (!bookingResponse.getStatusCode().is2xxSuccessful()) {
            return bookingResponse;
        }

        Booking booking = (Booking) bookingResponse.getBody();
        if (booking == null) {
            return ResponseEntity.status(404).body("Booking not found");
        }

        //fetch eventID using booking
        ResponseEntity<Event> eventResponse = restTemplate.getForEntity(eventBaseUrl + "/searchById/" + booking.getEventId(), Event.class);
        if (!eventResponse.getStatusCode().is2xxSuccessful()) {
            return eventResponse;
        }

        Event event = (Event) eventResponse.getBody();
        if (event == null) {
            return ResponseEntity.status(404).body("Event not found for booking");
        }

        //fetch ticket using ticketId
        ResponseEntity<Ticket> ticketResponse = restTemplate.getForEntity(ticketBaseUrl + "/getTicketById/" + ticketId, Ticket.class);
        if (!ticketResponse.getStatusCode().is2xxSuccessful()) {
            return ticketResponse;
        }

        Ticket ticket = (Ticket) ticketResponse.getBody();
        if (ticket == null) {
            return ResponseEntity.status(404).body("Ticket not found");
        }

        // check whether event is valid for today
        boolean isEventForToday = event.getEventDate().isEqual(LocalDate.now());

        if (isEventForToday) {

            // Update the ticket's attendance status
            ResponseEntity<?> updateResponse = restTemplate.getForEntity(ticketBaseUrl + "/updateTicketAttendance/" + ticketId, Ticket.class);
            if (!updateResponse.getStatusCode().is2xxSuccessful()) {
                return updateResponse; // update attendance fail
            }
            return ResponseEntity.ok("Ticket is valid and for today's event. Attendance has been updated.");
        } 

        else {
            return ResponseEntity.ok("Ticket is not for today's event.");
        }
        
    }

    public ResponseEntity<?> processOnsiteTicketSales(int eventId, int numOfTickets, int customerId) {
        // Validate the event and check ticket availability
        ResponseEntity<Event> eventResponse = restTemplate.getForEntity(eventBaseUrl + "/searchById/" + eventId, Event.class);
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
        ResponseEntity<Event> updateResponse = restTemplate.postForEntity(eventBaseUrl + "/editEvent/" + eventId, eventCreateDTO, Event.class);
        if (!updateResponse.getStatusCode().is2xxSuccessful()) {
            return updateResponse; 
        }
    
        // Create a new booking for the onsite ticket sales.
        Booking bookingCreate = new Booking();
        bookingCreate.setEventId(eventId);
        bookingCreate.setNumOfTickets(numOfTickets);
        bookingCreate.setUserId(customerId); 
    
        // Call the booking service to create the booking.
        ResponseEntity<Booking> bookingResponse = restTemplate.postForEntity(bookingBaseUrl + "/createBooking/" + bookingCreate, Booking.class);
        if (!bookingResponse.getStatusCode().is2xxSuccessful()) {
            return bookingResponse;
        }
    
        // Return successful response
        return ResponseEntity.ok().body("Onsite ticket sales processed successfully.");
    }

    public ResponseEntity<?> issueETicket(int bookingId, String customerEmail) {

        // Fetch booking details
        ResponseEntity<Booking> bookingResponse = restTemplate.getForEntity(bookingBaseUrl + "/getOneBooking/" + bookingId, Booking.class);
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

        ResponseEntity<?> emailResponse = restTemplate.getForEntity(emailBaseUrl + "/sendEmail/" + customerEmail + "/Your E-Ticket/" + eTicketInfo, String.class);

        // Return successful response
        return ResponseEntity.ok("E-Ticket issued successfully to " + customerEmail);
    }
    
}
