// Testing purpose: Can comment out
package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public Customer createCustomer(@RequestParam String user_fname,
                                   @RequestParam String user_lname,
                                   @RequestParam String email,
                                   @RequestParam String password) {
        return customerService.createCustomer(user_fname, user_lname, email, password);
    }
    

    @PutMapping("/reduce-balance/{userId}")
    public ResponseEntity<String> reduceCustomerBalance(@PathVariable int userId,
                                                        @RequestParam double amount) {
        try {
            customerService.reduceCustomerBalance(userId, Double.valueOf(amount));
            return ResponseEntity.ok("Balance reduced successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/increase-balance/{userId}")
    public ResponseEntity<String> increaseCustomerBalance(@PathVariable int userId,
                                                          @RequestParam double amount) {
        try {
            customerService.increaseCustomerBalance(userId, amount);
            return ResponseEntity.ok("Balance increased successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
