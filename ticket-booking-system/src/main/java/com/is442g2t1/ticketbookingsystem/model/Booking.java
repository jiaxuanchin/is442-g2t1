package com.is442g2t1.ticketbookingsystem.model;


// import javax.persistence.Entity;
// import javax.persistence.Id;
// import javax.persistence.Column;
import java.time.LocalDateTime;


public class Booking {

    private String bookingId; // Unique identifier for the booking
    private String customerId; // ID of the customer who made the booking
    private String eventId; // ID of the event for which the booking is made
    private int numberOfTickets; // Number of tickets booked
    private double totalPrice; // Total price paid for the booking
    private LocalDateTime bookingDate; // Date and time when the booking was made

    // Default Constructor
    public Booking() {
    }

    // Full constructor
    public Booking(String bookingId, String customerId, String eventId, int numberOfTickets, double totalPrice, LocalDateTime bookingDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.eventId = eventId;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
    }

    // Getters and setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

}


