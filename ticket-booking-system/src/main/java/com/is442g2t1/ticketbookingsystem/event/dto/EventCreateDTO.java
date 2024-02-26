package com.is442g2t1.ticketbookingsystem.event.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventCreateDTO {
    private String eventTitle;
    private LocalDate eventDate;
    private String eventDesc;
    private String eventLoc;
    private String startTime;
    private String endTime;
    private Integer filled;
    private Integer capacity;
}
