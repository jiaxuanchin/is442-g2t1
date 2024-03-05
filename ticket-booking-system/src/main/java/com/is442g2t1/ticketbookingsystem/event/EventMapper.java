package com.is442g2t1.ticketbookingsystem.event;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;

    public EventMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Event mapToEvent(EventCreateDTO eventCreateDTO) {
        return modelMapper.map(eventCreateDTO, Event.class);
    }
}
