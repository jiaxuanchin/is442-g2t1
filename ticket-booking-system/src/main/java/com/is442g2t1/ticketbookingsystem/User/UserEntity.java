package com.is442g2t1.ticketbookingsystem.User;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.is442g2t1.ticketbookingsystem.security.token.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
@Data // create the getter and setter functions for us
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Creates a single table strategy for each class hierarchy
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING) // Column used to differentiate the subclass in the single table
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

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

    @Column(name = "user_type" , insertable = false, updatable = false)
    private String user_type = "customer";

    public UserEntity(Role role, String user_fname, String user_lname, String email, String password){
        this.role = role;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.email = email;
        this.password = password;
    }

    // public UserEntity(Role role, String user_fname, String user_lname, String email, String password, double balance){
    //     this.role = role;
    //     this.user_fname = user_fname;
    //     this.user_lname = user_lname;
    //     this.email = email;
    //     this.password = password;
    // }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Token> tokens;
}
