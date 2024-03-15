package com.is442g2t1.ticketbookingsystem.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/UserEntity")
public class UserEntityController {

    private UserEntityService userEntityService;

    @Autowired
    public UserEntityController (UserEntityService userEntityService){
        this.userEntityService = userEntityService;
    }

    //Just for testing: Can comment out, register will do this job
    @PostMapping("/create")
    public UserEntity createUser(@RequestParam String roleName,
                                 @RequestParam String user_fname,
                                 @RequestParam String user_lname,
                                 @RequestParam String email,
                                 @RequestParam String password) {
        return userEntityService.createUser(roleName, user_fname, user_lname, email, password);
    }

    // This is required: Allow users to update their profile
    @PutMapping("/update/{userId}")
    public UserEntity updateUserProfile(@PathVariable String userId,
                                        @RequestParam String user_fname,
                                        @RequestParam String user_lname,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        return userEntityService.updateUserProfile(Integer.parseInt(userId), user_fname, user_lname, email, password);
    }

}
