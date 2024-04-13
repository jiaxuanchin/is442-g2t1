package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/eventmanager") 
public class EventManagerController {

    private final EventManagerService eventManagerService;

    @Autowired
    public EventManagerController(EventManagerService eventManagerService) {
        this.eventManagerService = eventManagerService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDTO eventCreateDTO) {
        return eventManagerService.createEvent(eventCreateDTO);
    }

    @PutMapping("/update/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> updateEventDetails(@PathVariable Integer eventId, @RequestBody EventCreateDTO eventDTO) {
        return eventManagerService.updateEventDetails(eventId, eventDTO);
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> getEventById(@PathVariable Integer eventId) {
        return eventManagerService.searchEventById(eventId);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> searchEventByTitle(@PathVariable String title) {
        return eventManagerService.searchEventByTitle(title);
    }

    @GetMapping("/location/{location}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> searchEventByLocation(@PathVariable String location) {
        return eventManagerService.searchEventByLocation(location);
    }

    @DeleteMapping("/delete/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventId) {
        return eventManagerService.deleteEvent(eventId);
    }

    @GetMapping("/statistics/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> generateSalesStatistics(@PathVariable Integer eventId, HttpServletResponse response) {
        return eventManagerService.generateSalesStatistics(eventId, response);
    }

}
