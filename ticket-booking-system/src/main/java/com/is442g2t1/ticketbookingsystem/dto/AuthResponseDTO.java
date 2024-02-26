package com.is442g2t1.ticketbookingsystem.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer"; // Bearer authentication scheme

    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
