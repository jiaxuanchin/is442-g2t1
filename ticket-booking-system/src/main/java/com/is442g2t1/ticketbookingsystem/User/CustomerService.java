package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createCustomer(String user_fname, String user_lname, String email, String password) {
        try {
            ResponseEntity<?> customerRoleResponse = roleService.getRoleByName("customer");
            if (customerRoleResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Role 'customer' not found in the database.");
            }

            Role customerRole = (Role) customerRoleResponse.getBody();
            Customer newCustomer = new Customer(user_fname, user_lname, email, password);
            newCustomer.setRole(customerRole);
            userRepository.save(newCustomer);

            return ResponseEntity.ok(newCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Method to reduce customer's balance
    public ResponseEntity<?> reduceCustomerBalance(int userId, double amount) {
        try {
            Customer existingCustomer = (Customer) userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + userId));

            existingCustomer.reduceBalance(amount);
            userRepository.save(existingCustomer);
            return ResponseEntity.ok("Customer balance reduced successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Method to increase customer's balance
    public ResponseEntity<?> increaseCustomerBalance(int userId, double amount) {
        try {
            Customer existingCustomer = (Customer) userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + userId));

            existingCustomer.increaseBalance(amount);
            userRepository.save(existingCustomer);
            return ResponseEntity.ok("Customer balance increased successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
