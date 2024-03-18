package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data  // create the getter and setter functions for us
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("event_manager")

public class EventManager extends UserEntity{
    
}
