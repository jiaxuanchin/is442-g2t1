package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.is442g2t1.ticketbookingsystem.ticket.*;
import com.is442g2t1.ticketbookingsystem.event.*;

import java.time.LocalDateTime;

@Service
public class TicketingOfficerService {

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Inject RoleRepository instance

    @Autowired
    private EventRepository eventRepository;

    // Verify ticket validity
    public boolean verifyTicket(int ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findTicketByTicketId(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            
            // Check ticket validity
            return ticket.isValid() && ticket.getEvent().getEventDateTime().isAfter(LocalDateTime.now());
        }
        return false; // Ticket not found or invalid
    }

    // Method to create a new ticketing officer
    @Transactional
    public UserEntity createTicketingOfficer(String firstName, String lastName, String email, String password) {
        UserEntity ticketingOfficer = new UserEntity();
        ticketingOfficer.setUser_fname(firstName);
        ticketingOfficer.setUser_lname(lastName);
        ticketingOfficer.setEmail(email);
        ticketingOfficer.setPassword(password);
        ticketingOfficer.setRole(roleRepository.findByName("ticketing_officer").orElseThrow(() -> new IllegalArgumentException("Role not found: TICKETING_OFFICER")));
        
        // Save the ticketing officer to the database
        return userRepository.save(ticketingOfficer);
    }


    //Process on-site ticket sales
    @Transactional
    public void processOnSiteSale(int eventId, int numOfTickets) throws Exception {
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
    public Ticket issueETicket(int customerId, int eventId, int numOfTickets) throws Exception {
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
