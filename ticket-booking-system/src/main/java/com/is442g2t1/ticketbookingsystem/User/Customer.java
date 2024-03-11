package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.NoArgsConstructor; // Create the constructors for us

import javax.persistence.*;

@Entity
@Data  // create the getter and setter functions for us
@NoArgsConstructor // create constructors for us
@DiscriminatorValue("customer")

public class Customer extends UserEntity{
    @Column(name = "balance")
    private double balance;

    // To reduce balance
    public void reduceBalance(double amount) {
        if (amount >= 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid amount or insufficient balance");
        }
    }
}
