package com.is442g2t1.ticketbookingsystem.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EventManagers") 
public class EventManager extends UserEntity {

    // Constructors
    public EventManager() {
        super(); 
    }

}
