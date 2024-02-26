package com.is442g2t1.ticketbookingsystem.model;

// import javax.persistence.Entity;
// import javax.persistence.Id;
// import javax.persistence.Column;
// import javax.persistence.Table;
import java.io.Serializable;

// @Entity
// @Table(name = "users") // Specifies the details of the table that will be used to create the table in the database
public class User implements Serializable {

    // @Id // Marks the id as the primary key
    private String id; // Unique identifier for the user

    // @Column(nullable = false, unique = true)
    private String email; // User's email address

    // @Column(nullable = false)
    private String password; // User's password

    // @Column(nullable = false)
    private String name; // User's full name

    // Constructors
    public User() {
        // Default constructor required for JPA
    }

    public User(String id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

