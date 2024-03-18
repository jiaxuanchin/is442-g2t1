package com.is442g2t1.ticketbookingsystem.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;

public class UserDetailsImpl implements UserDetails{
    
    private int id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(int id, String username, String email, String password, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;

    }

    public static UserDetailsImpl build(UserEntity user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        // Concatenating userFirstName and userLastName to form a username
        String fullName = user.getUser_fname() + " " + user.getUser_lname();

        return new UserDetailsImpl(
            user.getId(), 
            fullName,
            user.getEmail(),
            user.getPassword(), 
            Collections.singletonList(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        return true;
        if (o == null || getClass() != o.getClass())
        return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
