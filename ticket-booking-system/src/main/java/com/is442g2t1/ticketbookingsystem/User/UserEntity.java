package com.is442g2t1.ticketbookingsystem.User;

import lombok.Data;
import lombok.NoArgsConstructor; // Create the constructors for us

import javax.persistence.*;

@Entity
@Table(name="Users")
@Data  // create the getter and setter functions for us
@NoArgsConstructor // create constructors for us
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Creates a single table strategy for each class hierarchy
@DiscriminatorColumn(name = "user_type") // Column used to differentiate the subclass in the single table

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    // Need to join this role id to the Roles table
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "user_fname")
    private String user_fname;

    @Column(name = "user_lname")
    private String user_lname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
