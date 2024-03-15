package com.is442g2t1.ticketbookingsystem.User;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data  // create the getter and setter functions for us
@EqualsAndHashCode(callSuper=false) // doesnt call to super, not sure if need
@DiscriminatorValue("ticketing_officers") // For the single inheritance table on User

public class TicketingOfficer extends UserEntity {

    private String assignedVenue;

    public TicketingOfficer(String user_fname, String user_lname, String email, String password, String assignedVenue) {
        super(new Role("ticketing_officers"), user_fname,user_lname,email,password); // Call user superclass
        this.assignedVenue = assignedVenue;
    }

    // Getters and setters
    public String getAssignedVenue() {
        return assignedVenue;
    }

    public void setAssignedVenue(String assignedVenue) {
        this.assignedVenue = assignedVenue;
    }

}