package com.is442g2t1.ticketbookingsystem.User.DTO;

import lombok.Data;

@Data // provides getter and setter method
public class RegisterDto {
    private String user_fname;
    private String user_lname;
    private String email;
    private String password;
}
