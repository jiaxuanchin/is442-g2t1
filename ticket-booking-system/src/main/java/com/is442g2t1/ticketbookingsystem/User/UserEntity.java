package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.NoArgsConstructor; // Create the constructors for us

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users")
@Data  // create the getter and setter functions for us
@NoArgsConstructor // create constructors for us

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", 
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "user_fname")
    private String user_fname;

    @Column(name = "user_lname")
    private String user_lname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

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
