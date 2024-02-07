package com.is442g2t1.ticketbookingsystem.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@ToString
@Table(name = "Ticket")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @NonNull
    private int bookingId;

    @ManyToOne
    private Booking booking;
}
