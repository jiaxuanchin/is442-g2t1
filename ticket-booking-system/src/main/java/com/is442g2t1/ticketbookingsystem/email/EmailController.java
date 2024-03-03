package com.is442g2t1.ticketbookingsystem.email;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail() {
        emailService.sendEmail("euniceong.2021@scis.smu.edu.sg", "Event Booking Confirmation", "This is to confirm your booking. Thank you!");
        return "Confirmation Email Sent Successfully";
    }

    @RequestMapping("/sendCancellationEmail")
    public String sendCancellationEmail() {
        emailService.sendEmail("euniceong.2021@scis.smu.edu.sg", "Event Booking Cancellation", "This is to confirm your cancellation. Thank you!");
        return "Confirmation Email Sent Successfully";
    }

    @RequestMapping("/sendMassEmail")
    public String sendMassEmail() {
        emailService.sendEmail("euniceong.2021@scis.smu.edu.sg", "Event Cancellation", "Test Body");
        return "Event Cancellation Email Sent Successfully";
    }
}
