package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data  // create the getter and setter functions for us
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("customer")

public class Customer extends UserEntity{
    @Column(name = "balance")
    private double balance;

    public Customer(String user_fname, String user_lname, String email, String password, double balance){
        super(new Role("customer"),user_fname,user_lname,email,password);
        this.balance = balance;
    }

    // To reduce balance
    public void reduceBalance(double amount) {
        if (amount >= 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid amount or insufficient balance");
        }
    }
}
