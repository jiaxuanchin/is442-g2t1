package com.is442g2t1.ticketbookingsystem.User;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data  // create the getter and setter functions for us
@NoArgsConstructor // create constructors for us
@DiscriminatorValue("ticketing_officers") // For the single inheritance table on User

public class TicketingOfficer extends UserEntity {

    private String assignedVenue;

    // //Default Constructor
    // public TicketingOfficer() {
    // }

    // public TicketingOfficer(String id, String email, String password, String name, String assignedVenue) {
    //     super(id, email, password, name); // Call user superclass
    //     this.assignedVenue = assignedVenue;
    // }

    // // Getters and setters
    // public String getAssignedVenue() {
    //     return assignedVenue;
    // }

    // public void setAssignedVenue(String assignedVenue) {
    //     this.assignedVenue = assignedVenue;
    // }

}