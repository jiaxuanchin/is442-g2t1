package com.is442g2t1.ticketbookingsystem.User;

// import com.is442g2t1.ticketbookingsystem.User.Event;
// import com.is442g2t1.ticketbookingsystem.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventManagerService {

    @Autowired
    private EventRepository eventRepository;

    //create a new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(String eventId, Event updatedEvent) {
        return eventRepository.findById(eventId).map(event -> {
            event.setEventName(updatedEvent.getEventName());
            event.setEventVenue(updatedEvent.getEventVenue());
            // Update other fields as necessary
            return eventRepository.save(event);
        }).orElseGet(() -> {
            updatedEvent.setEventID(eventId);
            return eventRepository.save(updatedEvent);
        });
    }


    //delete an event
    public void deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);
    }

    //get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

   
}
