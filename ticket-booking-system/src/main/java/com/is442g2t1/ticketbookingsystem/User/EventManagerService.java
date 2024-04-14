package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.response.SuccessResponse;
import com.is442g2t1.ticketbookingsystem.event.Event;
import com.is442g2t1.ticketbookingsystem.event.EventService;
import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import com.is442g2t1.ticketbookingsystem.event.EventRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;


@Service
public class EventManagerService {

    private final RestTemplate restTemplate;
    private final EventService eventService;
    private final String eventBaseUrl = "http://localhost:8080/event";

    @Autowired
    public EventManagerService(RestTemplate restTemplate, EventService eventService) {
        this.restTemplate = restTemplate;
        this.eventService = eventService;
        
    }

    // // Create a new event
    public ResponseEntity<?> createEvent(EventCreateDTO eventCreateDTO) {
        return restTemplate.postForEntity(eventBaseUrl + "/createEvent", eventCreateDTO, ResponseEntity.class);
    }

    // Update event details
    public ResponseEntity<?> updateEventDetails(Integer eventId, EventCreateDTO eventDTO) {
        restTemplate.put(eventBaseUrl + "/editEvent/" + eventId, eventDTO);
        return ResponseEntity.ok().build(); // Assuming successful update
    }

    // Search for an event by its ID
    public ResponseEntity<?> searchEventById(Integer eventId) {
        return restTemplate.getForEntity(eventBaseUrl + "/searchById/" + eventId, ResponseEntity.class);
    }

    // search for an event by its title
    public ResponseEntity<?> searchEventByTitle(String title) {
        return restTemplate.getForEntity(eventBaseUrl + "/searchByTitle/" + title, ResponseEntity.class);
    }

    // Search for an event by its location
    public ResponseEntity<?> searchEventByLocation(String location) {
        return restTemplate.getForEntity(eventBaseUrl + "/searchByLocation/" + location, ResponseEntity.class);
    }

    // Delete an event
    public ResponseEntity<?> deleteEvent(Integer eventId) {
        restTemplate.delete(eventBaseUrl + "/deleteById/" + eventId);
        return ResponseEntity.ok().build(); 
    }

    /*  View sales statistics
        Number of tickets sold per event
        Revenue generated per event
        Cancel events if necessary
    */

    public ResponseEntity<?> generateSalesStatistics(Integer eventId, HttpServletResponse response) {
        
        ResponseEntity<?> responseEntity = eventService.searchById(eventId);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(responseEntity.getStatusCode()).body("Event not found or error occurred");
        }

        Object responseBody = responseEntity.getBody();
        if (!(responseBody instanceof SuccessResponse)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected response body type");
        }

        SuccessResponse successResponse = (SuccessResponse) responseBody;
        Object data = successResponse.getData();
        if (!(data instanceof Event)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data type is not Event");
        }

        Event event = (Event) data;

        if (event.getCapacity() == null || event.getFilled() == null || event.getTicketPrice() == null) {
            return ResponseEntity.badRequest().body("Essential event details are missing.");
        }

        Integer totalNumTickets = event.getCapacity();
        Integer ticketsAvailable = totalNumTickets - event.getFilled();
        Integer ticketsSold = totalNumTickets - ticketsAvailable;
        double revenue = ticketsSold * event.getTicketPrice();
        Integer customerAttendance = ticketsSold;

        String csvContent = String.format(
                "Event Name, Total Tickets, Tickets Sold, Tickets Available, Customer Attendance, Ticket Sales\n%s, %d, %d, %d, %d, %.2f",
                event.getEventTitle(), totalNumTickets, ticketsSold, ticketsAvailable, customerAttendance, revenue
        );

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"SalesStatistics_Event_" + eventId + ".csv\"");
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(csvContent.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
            return null; // Return null because response is handled through HttpServletResponse
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to generate report: " + e.getMessage());
        }
    }

    
}
    



