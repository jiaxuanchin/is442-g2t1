package com.is442g2t1.ticketbookingsystem.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("ticketing_officer")
@Data
public class TicketingOfficer extends UserEntity {

    public TicketingOfficer() {
        super();
    }

    public TicketingOfficer(Role role, String user_fname, String user_lname, String email, String password) {
        super(role, user_fname, user_lname, email, password);
    }
    
}
