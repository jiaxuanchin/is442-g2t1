package com.is442g2t1.ticketbookingsystem.User;

// import com.is442g2t1.ticketbookingsystem.User.Event;
// import com.is442g2t1.ticketbookingsystem.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// import java.util.Optional;

import com.is442g2t1.ticketbookingsystem.event.*;

@Service
public class EventManagerService {

    @Autowired
    private EventRepository eventRepository;

    //create a new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(int eventId, Event updatedEvent) {
        return eventRepository.findById(eventId).map(event -> {
            event.setEventTitle(updatedEvent.getEventTitle());
            event.setEventLoc(updatedEvent.getEventLoc());
            // Update other fields as necessary
            return eventRepository.save(event);
        }).orElseGet(() -> {
            updatedEvent.setEventId(eventId);
            return eventRepository.save(updatedEvent);
        });
    }


    //delete an event
    public void deleteEvent(int eventId) {
        eventRepository.deleteEventById(eventId);
    }

    //get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // to edit - salesreport 
    //     // Generate a sales report for an event
    // public SalesReport generateSalesReport(String eventId) {
    //     Optional<Event> eventOpt = eventRepository.findById(eventId);

    //     if (!eventOpt.isPresent()) {
    //         // event does not exist
    //         throw new RuntimeException("Event not found with ID: " + eventId); 
    //     }

    //     Event event = eventOpt.get();
    //     SalesReport salesReport = new SalesReport();

    //     // Calculate tickets sold & total revenue
    //     int ticketsSold = event.getTotalNumTickets() - event.getNumTicketsAvailable();
    //     double totalRevenue = calculateTotalRevenue(event.viewEventPrice(), ticketsSold);
        
    //     // cancellation rate 
    //     double cancellationRate = calculateCancellationRate(event.getTotalNumTickets(), ticketsSold); 

    //     // Generate sales report
    //     salesReport.setEvent(event);
    //     salesReport.setTicketsSold(ticketsSold);
    //     salesReport.setTotalRevenue(totalRevenue);
    //     salesReport.setCancellationRate(cancellationRate);

    //     // Save  report --> generate as excel
    //     return salesReportRepository.save(salesReport);
    // }

    // // Calculate total revenue
    // private double calculateTotalRevenue(double ticketPrice, int ticketsSold) {
    //     return ticketPrice * ticketsSold;
    // }

    // // Calculate cancellation rate
    // private double calculateCancellationRate(int totalTickets, int ticketsSold) {
    //     int ticketsCancelled = totalTickets - ticketsSold;
    //     return totalTickets > 0 ? (double) ticketsCancelled / totalTickets * 100 : 0;
    // }

   
}
