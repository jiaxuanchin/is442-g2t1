package com.is442g2t1.ticketbookingsystem.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TicketingOfficers")
public class TicketingOfficer extends UserEntity {

    // Constructors
    public TicketingOfficer() {
        super();
    }

}
