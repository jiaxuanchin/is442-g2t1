package com.is442g2t1.ticketbookingsystem.event;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvent() {
        return eventService.getAllEvent();
    }

    @PostMapping("/createEvent")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @PutMapping("/editEvent/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> editEvent(@RequestBody EventCreateDTO eventDTO, @PathVariable Integer eventId) {
        System.out.println("[HEEEERRRREEEEEE] EventController: editEvent");
        return eventService.editEvent(eventDTO, eventId);
    }

    @GetMapping("/searchById/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> searchById(@PathVariable Integer eventId) {
        return eventService.searchById(eventId);
    }

    @GetMapping("/searchByTitle/{eventTitle}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> searchByTitle(@PathVariable String eventTitle) {
        return eventService.searchByTitle(eventTitle);
    }

    @GetMapping("/searchByLocation/{eventLoc}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> getByLocation(@PathVariable String eventLoc) {
        return eventService.searchByLocation(eventLoc);
    }

    @DeleteMapping("/cancelEvent/{eventId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> cancelEvent(@PathVariable Integer eventId) {
        return eventService.cancelEvent(eventId);
    }
}
