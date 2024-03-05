package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;


public class TicketingOfficerService {

    private final RestTemplate restTemplate;
    private String eventServiceBaseUrl = "http://localhost:8080/event";
    private String ticketServiceBaseUrl = "http://localhost:8080/ticket";

    public TicketingOfficerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Verify ticket validity
    public boolean verifyTicket(int ticketId) {
        ResponseEntity<Boolean> response = restTemplate.getForEntity(ticketServiceBaseUrl + "/verify/" + ticketId, Boolean.class);
        return response.getBody();
    }

    // Process on-site ticket sales
    public ResponseEntity<?> processOnSiteSale(String eventId, int numOfTickets) {
        ResponseEntity<?> response = restTemplate.postForEntity(eventServiceBaseUrl + "/processSale/" + eventId + "/" + numOfTickets, null, ResponseEntity.class);
        return response;
    }

    // Issuing e-tickets
    public ResponseEntity<?> issueETicket(String customerId, String eventId, int numOfTickets) {
        ResponseEntity<?> response = restTemplate.postForEntity(ticketServiceBaseUrl + "/issue/" + customerId + "/" + eventId + "/" + numOfTickets, null, ResponseEntity.class);
        return response;
    }
}
