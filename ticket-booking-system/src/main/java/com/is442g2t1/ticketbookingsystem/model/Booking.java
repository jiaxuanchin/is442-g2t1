package com.is442g2t1.ticketbookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.util.List;

import io.micrometer.common.lang.NonNull;

@Entity
@ToString
@Getter
@Setter
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @NonNull
    private int userId;
    
    @NonNull
    private int eventId;
    
    @NonNull
    private int numOfTickets;

    @OneToMany(mappedBy = "bookingId")
    private List<Ticket> tickets;


    private Timestamp booking_timestamp;

    public Booking(int userId, int eventId, int numOfTickets) {
        this.userId = userId;
        this.eventId = eventId;
        this.numOfTickets = numOfTickets;
    }

    // public int getBooking_id() {
    //     return bookingId;
    // }
    // public int getUser_id() {
    //     return userId;
    // }
    // public void setUser_id(int userId) {
    //     this.userId = userId;
    // }
    // public int getEvent_id() {
    //     return eventId;
    // }
    // public void setEvent_id(int eventId) {
    //     this.eventId = eventId;
    // }
    // public int getNumber_of_tickets() {
    //     return numOfTickets;
    // }
    // public void setNumber_of_tickets(int numOfTickets) {
    //     this.numOfTickets = numOfTickets;
    // }
    // public Timestamp getBooking_timestamp() {
    //     return booking_timestamp;
    // }
}
