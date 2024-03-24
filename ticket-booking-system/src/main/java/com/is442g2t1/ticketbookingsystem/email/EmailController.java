package com.is442g2t1.ticketbookingsystem.email;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin
@RequestMapping("/email")
public class EmailController {
    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail() {
        try {
            emailService.sendEmail("g2t1.is442@gmail.com", "Event Booking Confirmation", "This is to confirm your booking. Thank you!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Confirmation Email Sent Successfully";
    }

    @RequestMapping("/sendCancellationEmail")
    public String sendCancellationEmail() {
        try {
            emailService.sendEmail("g2t1.is442@gmail.com", "Event Booking Cancellation", "This is to confirm your cancellation. Your payment has been refunded into your account wallet. Thank you!");
        } catch (MessagingException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return "Cancellation Email Sent Successfully";
    }

    @RequestMapping("/sendMassEmail")
    public String sendMassEmail() {
        try {
            emailService.sendEmail("g2t1.is442@gmail.com", "Event Cancellation", "Sorry to inform you that the event has been cancelled. Your payment has been refunded into your account wallet. Do take a look at some other upcoming events that we have. Thank you!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Event Cancellation Email Sent Successfully";
    }
}
