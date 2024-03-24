package com.is442g2t1.ticketbookingsystem.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.is442g2t1.ticketbookingsystem.ticket.Ticket;
import com.is442g2t1.ticketbookingsystem.event.Event;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;

import io.micrometer.common.lang.NonNull;


@Entity
@ToString
@Getter
@Setter
@Table(
    name = "Booking",
    uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "user_id"})
    )
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "user_id")
    @NonNull
    private int userId;
    
    // ----------------------- to be edited when we integrate with events class -----------------------
    @Column(name = "event_id")
    @NonNull
    private int eventId;
    
    @Column(name = "number_of_tickets")
    @NonNull
    private int numOfTickets;

    @Column(name = "booking_timestamp", nullable = false, updatable = false, insertable = false)
    private Timestamp booking_timestamp;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Ticket> tickets;

    public Booking() {
    }

    public Booking(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public Booking(int userId, int eventId, int numOfTickets) {
        this.userId = userId;
        this.eventId = eventId;
        this.numOfTickets = numOfTickets;
    }

}
