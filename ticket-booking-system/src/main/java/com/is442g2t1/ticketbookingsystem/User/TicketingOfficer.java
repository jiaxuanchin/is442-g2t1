package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data  // create the getter and setter functions for us
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("ticketing_officer")

public class TicketingOfficer extends UserEntity{
    // Constructor without RoleService dependency
    public TicketingOfficer(String user_fname, String user_lname, String email, String password) {
        super(null, user_fname, user_lname, email, password);
    }

}
