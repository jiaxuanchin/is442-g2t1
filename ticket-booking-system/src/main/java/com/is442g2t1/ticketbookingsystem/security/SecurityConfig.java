package com.is442g2t1.ticketbookingsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import com.is442g2t1.ticketbookingsystem.security.jwt.JWTAuthFilter;
import com.is442g2t1.ticketbookingsystem.security.service.UserDetailsServiceImpl;
import com.is442g2t1.ticketbookingsystem.security.jwt.JWTAuthEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private JWTAuthEntryPoint authEntryPoint;
    private UserDetailsServiceImpl userDetailsService;

    // @Autowired
    // public SecurityConfig(UserDetailsServiceImpl userDetailsService, JWTAuthEntryPoint authEntryPoint) {
    //     this.userDetailsService = userDetailsService;
    //     this.authEntryPoint = authEntryPoint;

    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthFilter jwtAuthenticationFilter() {
        return new JWTAuthFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
    
        return authProvider;
    }

    // Todo: This part needs to change, needs to be more strict can see the github 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated()
            );
    
        return http.build();
    }

}