package com.is442g2t1.ticketbookingsystem.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends User {

    @Column(name = "account_balance", nullable = false)
    private double accountBalance;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>();

    public Customer() {
        int CustomerID ; 
        double accountBalance;
    }

    public Customer(String email, String password, double accountBalance) {
        super(email, new BCryptPasswordEncoder().encode(password)); // Password should be encrypted
        this.accountBalance = accountBalance;
    }

    // Business logic should be handled in the service layer, not in the entity

    // Getters and Setters
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setCustomer(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setCustomer(null);
    }

}
