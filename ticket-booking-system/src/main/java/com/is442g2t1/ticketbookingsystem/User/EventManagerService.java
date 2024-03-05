package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class EventManagerService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/event"; // Adjust this to your actual base URL

    @Autowired
    public EventManagerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Create a new event
    public ResponseEntity<?> createEvent(EventCreateDTO eventCreateDTO) {
        return restTemplate.postForEntity(baseUrl + "/createEvent", eventCreateDTO, ResponseEntity.class);
    }

    // Update event details
    public ResponseEntity<?> updateEventDetails(Integer eventId, EventCreateDTO eventDTO) {
        restTemplate.put(baseUrl + "/editEvent/" + eventId, eventDTO);
        return ResponseEntity.ok().build(); // Assuming successful update
    }

    // Search for an event by its ID
    public ResponseEntity<?> searchEventById(Integer eventId) {
        return restTemplate.getForEntity(baseUrl + "/searchById/" + eventId, ResponseEntity.class);
    }

    // search for an event by its title
    public ResponseEntity<?> searchEventByTitle(String title) {
        return restTemplate.getForEntity(baseUrl + "/searchByTitle/" + title, ResponseEntity.class);
    }

    // Search for an event by its location
    public ResponseEntity<?> searchEventByLocation(String location) {
        return restTemplate.getForEntity(baseUrl + "/searchByLocation/" + location, ResponseEntity.class);
    }

    // Delete an event
    public ResponseEntity<?> deleteEvent(Integer eventId) {
        restTemplate.delete(baseUrl + "/deleteById/" + eventId);
        return ResponseEntity.ok().build(); 
    }

}
