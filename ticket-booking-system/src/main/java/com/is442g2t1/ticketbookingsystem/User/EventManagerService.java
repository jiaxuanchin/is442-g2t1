package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.response.SuccessResponse;
import com.is442g2t1.ticketbookingsystem.event.Event;
import com.is442g2t1.ticketbookingsystem.event.EventService;
import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;


import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.Files;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class EventManagerService {

    private EventService eventService; 

    private final RestTemplate restTemplate;
    private final String eventBaseUrl = "http://localhost:8080/event";


    @Autowired
    public EventManagerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Create a new event
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

    public ResponseEntity<String> generateSalesStatistics(int eventId) {
        ResponseEntity<?> eventResponse = eventService.searchById(eventId);
        
        if (!eventResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(eventResponse.getStatusCode()).body("Event not found");
        }

        Event event = (Event) ((SuccessResponse) eventResponse.getBody()).getData();

        int totalNumTickets = event.getCapacity();
        int ticketsAvailable = event.getFilled();
        int ticketsSold = totalNumTickets - ticketsAvailable;
        double revenue = ticketsSold * event.getTicketPrice();
        // long customerAttendance = event.getFilled();

        String csvContent = String.format(
            "Event Name, Total Tickets, Tickets Sold, Tickets Available, Customer Attendance, Ticket Sales\n%s, %d, %d, %d, %d, %.2f",
            event.getEventTitle(), totalNumTickets, ticketsSold, ticketsAvailable, customerAttendance, revenue
        );

        String fileName = "SalesStatistics_Event_" + eventId + ".csv";
        try {
            Path path = Paths.get(fileName);
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write(csvContent);
            }
            return ResponseEntity.ok("Report generated: " + path.toAbsolutePath().toString());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to generate report: " + e.getMessage());
        }
    }

  
    
}
    



