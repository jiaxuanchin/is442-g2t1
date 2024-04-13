package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDTO eventCreateDTO) {
        return eventManagerService.createEvent(eventCreateDTO);
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<?> updateEventDetails(@PathVariable Integer eventId, @RequestBody EventCreateDTO eventDTO) {
        return eventManagerService.updateEventDetails(eventId, eventDTO);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Integer eventId) {
        return eventManagerService.searchEventById(eventId);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> searchEventByTitle(@PathVariable String title) {
        return eventManagerService.searchEventByTitle(title);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> searchEventByLocation(@PathVariable String location) {
        return eventManagerService.searchEventByLocation(location);
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventId) {
        return eventManagerService.deleteEvent(eventId);
    }

    @GetMapping("/statistics/{eventId}")
    public ResponseEntity<?> generateSalesStatistics(@PathVariable Integer eventId) {
        return eventManagerService.generateSalesStatistics(eventId);
    }

}
