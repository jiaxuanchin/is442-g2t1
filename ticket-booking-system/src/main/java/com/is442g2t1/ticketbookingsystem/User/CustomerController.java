// Testing purpose: Can comment out
package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Creation of a new user
    // @PostMapping("/create")
    // public ResponseEntity<?> createCustomer(@RequestParam String user_fname,
    //                                          @RequestParam String user_lname,
    //                                          @RequestParam String email,
    //                                          @RequestParam String password) {
    //     return customerService.createCustomer(user_fname, user_lname, email, password);
    // }

    // Reducing the user's balance
    @PutMapping("/reduce-balance/{userId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> reduceCustomerBalance(@PathVariable int userId,
                                                   @RequestParam double amount) {
        return customerService.reduceCustomerBalance(userId, amount);
    }

    // Increase the user's balance
    @PutMapping("/increase-balance/{userId}")
    public ResponseEntity<?> increaseCustomerBalance(@PathVariable int userId,
                                                      @RequestParam double amount) {
        return customerService.increaseCustomerBalance(userId, amount);
    }

    // Fetch the balance from the customer
    @GetMapping("/get-balance/{userId}")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity<?> getCustomerBalance(@PathVariable int userId){
        return customerService.getCustomerBalance(userId);
    }

    // Fetch the user password --> password

    // Update the user password --> password

}
