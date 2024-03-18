package com.is442g2t1.ticketbookingsystem.security.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer"; // Bearer authentication scheme
    private int id;
    private String username;
    private String email;
    private String role;

    public AuthResponseDTO(String accessToken, int id, String username, String email, String role) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}