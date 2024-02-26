package com.is442g2t1.ticketbookingsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor; // Create the constructors for us

import javax.persistence.*;

@Entity
@Table(name="Users")
@Data  // create the getter and setter functions for us
@NoArgsConstructor // create constructors for us

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private int roleId;

    @Column(name = "user_fname")
    private String user_fname;

    @Column(name = "user_lname")
    private String user_lname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

}
