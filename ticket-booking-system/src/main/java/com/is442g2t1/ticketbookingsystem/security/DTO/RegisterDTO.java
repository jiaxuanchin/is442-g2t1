package com.is442g2t1.ticketbookingsystem.security.DTO;

import lombok.Data;

@Data // provides getter and setter method
public class RegisterDTO {
    private String user_fname;
    private String user_lname;
    private String email;
    private String password;
}
