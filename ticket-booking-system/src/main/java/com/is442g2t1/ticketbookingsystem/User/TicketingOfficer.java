package com.is442g2t1.ticketbookingsystem.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("ticketing_officer")
@Data
@EqualsAndHashCode(callSuper=false)
public class TicketingOfficer extends UserEntity {

    public TicketingOfficer() {
        super();
    }
    
}
