package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.event.EventService;
import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import com.is442g2t1.ticketbookingsystem.ticket.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class EventManagerService {

    private final RestTemplate restTemplate;
    private final String eventBaseUrl = "http://localhost:8080/event";
    private final String ticketBaseUrl = "http://localhost:8080/ticket"; 


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

    /*View sales statistics
        Number of tickets sold per event
        Revenue generated per event
        Cancel events if necessary
    */

    public ResponseEntity<Map<String, Object>> viewSalesStatistics(int eventId) {
        // Fetch the event
        ResponseEntity<?> event = restTemplate.getForEntity(eventBaseUrl + "/getEventById/" + eventId, ResponseEntity.class);
        
        // i f
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
    
        // Fetch tickets associated with the event
        ResponseEntity<Ticket[]> ticketsResponseEntity = restTemplate.getForEntity(ticketBaseUrl + "/getTicketsByEventId/" + eventId, Ticket[].class);
        Ticket[] tickets = ticketsResponseEntity.getBody();
    
        // Calculate ticket sales and customer attendance
        int totalNumTickets = tickets.length;
        int numTicketsAvailable = 0;
        int ticketsSold = 0;
        int customerAttendance = 0;
    
        for (Ticket ticket : tickets) {
            if (ticket.getStatus().equals("Sold")) { // Assuming ticket status is 'Sold' when sold
                ticketsSold++;
            }
            if (!ticket.getStatus().equals("Cancelled")) { // Assuming attendance is not counted for cancelled tickets
                customerAttendance++;
            }
            if (ticket.getStatus().equals("Available")) {
                numTicketsAvailable++;
            }
        }
    
        // Calculate ticket sales using the formula: (eventTicketPrice) * (totalNumTicket - numTicketAvailable)
        int ticketSales = event.getTicketPrice() * (totalNumTickets - numTicketsAvailable);
    
        // Construct a map for sales statistics
        Map<String, Object> salesStatisticsMap = new HashMap<>();
        salesStatisticsMap.put("eventName", event.getName());
        salesStatisticsMap.put("ticketSales", ticketSales);
        salesStatisticsMap.put("customerAttendance", customerAttendance);
    
        return ResponseEntity.ok(salesStatisticsMap);
    }
    


}
