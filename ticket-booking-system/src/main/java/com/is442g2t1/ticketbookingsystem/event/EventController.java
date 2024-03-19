package com.is442g2t1.ticketbookingsystem.event;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @PutMapping("/editEvent/{eventId}")
    public ResponseEntity<?> editEvent(@RequestBody EventCreateDTO eventDTO, @PathVariable Integer eventId) {
        return eventService.editEvent(eventDTO, eventId);
    }

    @GetMapping("/searchById/{eventId}")
    public ResponseEntity<?> searchById(@PathVariable Integer eventId) {
        return eventService.searchById(eventId);
    }

    @GetMapping("/searchByTitle/{eventTitle}")
    public ResponseEntity<?> searchByTitle(@PathVariable String eventTitle) {
        return eventService.searchByTitle(eventTitle);
    }

    @GetMapping("/searchByLocation/{eventLoc}")
    public ResponseEntity<?> getByLocation(@PathVariable String eventLoc) {
        return eventService.searchByLocation(eventLoc);
    }

    @DeleteMapping("/deleteById/{eventId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer eventId) {
        return eventService.deleteById(eventId);
    }
}
