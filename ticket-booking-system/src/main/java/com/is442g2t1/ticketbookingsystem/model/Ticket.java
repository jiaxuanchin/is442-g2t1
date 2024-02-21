package com.is442g2t1.ticketbookingsystem.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(exclude = "booking")
@Table(name = "Ticket")
@Getter
@Setter
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    // @Column(name = "booking_id", nullable = false)
    // private int bookingId;

    @Column(name = "attendance")
    private boolean attendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Override
    public String toString() {
        
        if (booking != null) {
            return "Ticket{" +
                    "ticketId=" + ticketId +
                    ", bookingId=" + booking.getBookingId() +
                    ", attendance=" + attendance +
                    '}';
        }
        return "error: bookingId is null";
    }
}
