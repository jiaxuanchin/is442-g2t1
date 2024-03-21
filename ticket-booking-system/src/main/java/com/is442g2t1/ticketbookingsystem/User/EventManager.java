package com.is442g2t1.ticketbookingsystem.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;


@Entity
@DiscriminatorValue("event_manager")
@Data
public class EventManager extends UserEntity {
    
    public EventManager(Role role, String user_fname, String user_lname, String email, String password) {
        super(role, user_fname, user_lname, email, password);
    }
}
