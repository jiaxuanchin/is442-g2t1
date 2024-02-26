package com.is442g2t1.ticketbookingsystem.model;

import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private String customerId;
    private LocalDateTime issueDate;
    private boolean isValid;
    private Event event; //event object
    private Booking booking; //booking object

    // Default constructor
    public Ticket() {
    }

    // Full constructor
    public Ticket(String ticketId, String customerId, Event event, LocalDateTime issueDate, boolean isValid, Booking booking) {
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.event = event; 
        this.issueDate = issueDate;
        this.isValid = isValid;
        this.booking = booking; 
    }

    // Getters and setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
