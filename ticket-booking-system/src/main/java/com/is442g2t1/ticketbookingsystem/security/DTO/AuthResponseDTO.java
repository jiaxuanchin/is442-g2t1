package com.is442g2t1.ticketbookingsystem.security.DTO;


import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer"; // Bearer authentication scheme
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public AuthResponseDTO(String accessToken, Long id, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}