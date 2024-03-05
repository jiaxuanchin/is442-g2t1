package com.is442g2t1.ticketbookingsystem.event;
// model class

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_title")
    private String eventTitle;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_description")
    private String eventDesc;

    @Column(name = "event_location")
    private String eventLoc;

    @Column(name = "event_start_time")
    private String startTime;

    @Column(name = "event_end_time")
    private String endTime;

    @Column(name = "filled")
    private Integer filled;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @Column(name = "cancel_fee")
    private Double cancelFee;
}
