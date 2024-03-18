package com.is442g2t1.ticketbookingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.is442g2t1.ticketbookingsystem.security.service.UserDetailsServiceImpl;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void whenLoadUserByEmail_thenCorrectUser() {
        String email = "euniceong.2021@scis.smu.edu.sg"; // Use an email that exists in your test database
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().size() > 0); // Assuming the user has at least one role
    }
}
