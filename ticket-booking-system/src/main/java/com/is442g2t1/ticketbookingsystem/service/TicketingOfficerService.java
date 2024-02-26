package com.is442g2t1.ticketbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.is442g2t1.ticketbookingsystem.model.Event;
import com.is442g2t1.ticketbookingsystem.model.Ticket;
import com.is442g2t1.ticketbookingsystem.repository.TicketRepository;
import com.is442g2t1.ticketbookingsystem.repository.EventRepository;

import java.time.LocalDateTime;

@Service
public class TicketingOfficerService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    // Verify ticket validity
    public boolean verifyTicket(String ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            
            // Check ticket validity
            return ticket.isValid() && ticket.getEvent().getEventDateTime().isAfter(LocalDateTime.now());
        }
        return false; // Ticket not found or invalid
    }

    //Process on-site ticket sales
    @Transactional
    public void processOnSiteSale(String eventId, int numOfTickets) throws Exception {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new Exception("Event not found"));
        if (event.getNumTicketsAvailable() < numOfTickets) {
            throw new Exception("Not enough tickets available");
        }
        // Update event's available tickets
        event.setNumTicketsAvailable(event.getNumTicketsAvailable() - numOfTickets);
        eventRepository.save(event);
        //create and save tickets 
    }

    //Issuing e-tickets
    @Transactional
    public Ticket issueETicket(String customerId, String eventId, int numOfTickets) throws Exception {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new Exception("Event not found"));
        if (event.getNumTicketsAvailable() < numOfTickets) {
            throw new Exception("Not enough tickets available");
        }
        
        Ticket newTicket = new Ticket();
        newTicket.setCustomerId(customerId);
        newTicket.setEvent(event);
        newTicket.setIssueDate(LocalDateTime.now());
        newTicket.setValid(true);
        
        // Update available tickets count
        event.setNumTicketsAvailable(event.getNumTicketsAvailable() - numOfTickets);
        eventRepository.save(event);
        
        ticketRepository.save(newTicket);

        // sending an email to the customer 
        
        return newTicket; // return for further confirming or further processing
    }
}
