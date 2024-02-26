package com.is442g2t1.ticketbookingsystem.model;


import java.time.LocalDateTime;

public class Event {

    private String eventID; // Unique identifier for the event
    private String eventName; // Name of the event
    private String eventVenue; // Venue where the event will take place
    private LocalDateTime eventDateTime; // Date and time of the event
    private double eventTicketPrice; // Price per ticket
    private int totalNumTicket; // Total number of tickets available for the event
    private int numTicketAvailable; // Tickets currently available

   
    // Default constructor
    public Event() {
    }

    // Full constructor
    public Event(String eventID, String eventName, String eventVenue, LocalDateTime eventDateTime, double eventTicketPrice, int totalNumTicket, int numTicketAvailable) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventDateTime = eventDateTime;
        this.eventTicketPrice = eventTicketPrice;
        this.totalNumTicket = totalNumTicket;
        this.numTicketAvailable = numTicketAvailable;
    }

    // Getters and setters for all fields
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public double viewEventPrice() {
        return eventTicketPrice;
    }

    public void setTicketPrice(double eventTicketPrice) {
        this.eventTicketPrice = eventTicketPrice;
    }

    public int getTotalNumTickets() {
        return totalNumTicket;
    }

    public void setTotalTickets(int totalNumTicket) {
        this.totalNumTicket = totalNumTicket;
    }

    public int getNumTicketsAvailable() {
        return numTicketAvailable;
    }

    public void setNumTicketsAvailable(int numTicketAvailable) {
        this.numTicketAvailable = numTicketAvailable;
    }
}
