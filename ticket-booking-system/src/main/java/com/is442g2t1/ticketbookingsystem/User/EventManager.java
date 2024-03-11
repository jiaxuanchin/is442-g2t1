package com.is442g2t1.ticketbookingsystem.User;

import javax.persistence.*; // JPA API, to store, access and manage java objects

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data  // create the getter and setter functions for us
@NoArgsConstructor // create constructors for us
@DiscriminatorValue("event_manager") // For the single inheritance table on User

public class EventManager extends UserEntity {

    // // Default constructor
    // public EventManager() {
    //     super();
    // }

    // // Constructor
    // public EventManager(String id, String email, String password, String name) {
    //     super(id, email, password, name); 
    // }
    
}