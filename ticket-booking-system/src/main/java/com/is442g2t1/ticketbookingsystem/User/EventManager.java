package com.is442g2t1.ticketbookingsystem.User;

import javax.persistence.*; // JPA API, to store, access and manage java objects

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data  // create the getter and setter functions for us
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("event_manager") // For the single inheritance table on User

public class EventManager extends UserEntity {

    // Constructor
    public EventManager(String user_fname, String user_lname, String email, String password) {
        super(new Role("event_manager"),user_fname,user_lname,email,password); 
    }
    
}