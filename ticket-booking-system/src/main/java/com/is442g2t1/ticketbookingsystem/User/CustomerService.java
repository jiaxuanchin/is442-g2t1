package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public CustomerService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    // Method to create a new customer
    public Customer createCustomer(String user_fname, String user_lname, String email, String password) {
        Role customerRole = roleService.getRoleByName("customer");
        if (customerRole == null) {
            throw new IllegalStateException("Role 'customer' not found in the database.");
        }

        Customer newCustomer = new Customer(user_fname, user_lname, email, password);
        newCustomer.setRole(customerRole);
        return userRepository.save(newCustomer);
    }



    // Method to reduce customer's balance
    public void reduceCustomerBalance(int userId, double amount){
        Customer existingCustomer = (Customer) userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + userId));

        existingCustomer.reduceBalance(amount);
        userRepository.save(existingCustomer);
    }

    // Method to increase customer's balance
    public void increaseCustomerBalance(int userId, double amount){
        Customer existingCustomer = (Customer) userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + userId));

        existingCustomer.increaseBalance(amount);
        userRepository.save(existingCustomer);
    }

}
