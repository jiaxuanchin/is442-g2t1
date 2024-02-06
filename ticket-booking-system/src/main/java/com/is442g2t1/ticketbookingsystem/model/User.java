package com.is442g2t1.ticketbookingsystem.model;

public class User {

    private String email;
    private String password;

    // Constructors
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getter
    public String getEmail(){
        return this.email;
    }

    // Setter
    public void setPassword(String password){
        this.password = password;
    }
    
}
