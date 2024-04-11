package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data  // create the getter and setter functions for us
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("customer")

public class Customer extends UserEntity{
    @Column(name = "balance")
    private double balance;

    // Constructor without RoleService dependency
    public Customer(String user_fname, String user_lname, String email, String password) {
        super(null, user_fname, user_lname, email, password);
        this.balance = 1000;
    }

    // Method to reduce balance
    public void reduceBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance: " + balance);
        }
        balance -= amount;
    }

    // Method to increase balance
    public void increaseBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
        balance += amount;
    }
    
    // Fetch the user password --> password

    // Update the user password --> password
}
