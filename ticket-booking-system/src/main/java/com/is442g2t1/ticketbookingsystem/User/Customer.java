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

    // To reduce balance
    public void reduceBalance(double amount) {
        if (amount >= 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid amount or insufficient balance");
        }
    }

    // To increase balance
    public void increaseBalance(double amount) {
        balance += amount;
    }
}
