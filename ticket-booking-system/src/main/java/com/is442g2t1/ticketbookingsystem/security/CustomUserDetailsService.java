package com.is442g2t1.ticketbookingsystem.security;

import com.is442g2t1.ticketbookingsystem.User.Role;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;
import com.is442g2t1.ticketbookingsystem.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email was not found"));

        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(UserEntity user) {
        // Extracting user roles and converting them to Spring Security authorities
        Collection<GrantedAuthority> authorities = mapRolesToAuthorities(user.getRole());

        // Creating UserDetails object using user details and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    // Convert Role to GrantedAuthority
    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName())); //https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/authority/SimpleGrantedAuthority.html
    }
}
