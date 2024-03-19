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
    public ResponseEntity<?> createCustomer(@RequestParam String user_fname,
                                             @RequestParam String user_lname,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        return customerService.createCustomer(user_fname, user_lname, email, password);
    }

    @PutMapping("/reduce-balance/{userId}")
    public ResponseEntity<?> reduceCustomerBalance(@PathVariable int userId,
                                                   @RequestParam double amount) {
        return customerService.reduceCustomerBalance(userId, amount);
    }

    @PutMapping("/increase-balance/{userId}")
    public ResponseEntity<?> increaseCustomerBalance(@PathVariable int userId,
                                                      @RequestParam double amount) {
        return customerService.increaseCustomerBalance(userId, amount);
    }
}
