package com.is442g2t1.ticketbookingsystem.User;


// import javax.persistence.Entity;
// import javax.persistence.Table;

// @Entity
// @Table(name = "ticketing_officers") // Specifies the table in the database
public class TicketingOfficer extends User {

    private String assignedVenue;

    //Default Constructor
    public TicketingOfficer() {
    }

    public TicketingOfficer(String id, String email, String password, String name, String assignedVenue) {
        super(id, email, password, name); // Call user superclass
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



