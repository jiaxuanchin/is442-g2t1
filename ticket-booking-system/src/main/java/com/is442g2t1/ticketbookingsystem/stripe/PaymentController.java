package com.is442g2t1.ticketbookingsystem.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.cdimascio.dotenv.*;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${STRIPE_PUBLISHABLE_KEY}")
    private String stripePublishableKey;

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    @CrossOrigin
    @GetMapping("/config")
    public ResponseEntity getConfig() {
        PaymentDTO response = new PaymentDTO();
        response.setPublishableKey(stripePublishableKey);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @PostMapping("/create-payment-intent")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager', 'ticketing_officer')")
    public ResponseEntity createPaymentIntent(@RequestBody PaymentDTO paymentDTO) {
        System.out.println("reach payment intent please");
        System.out.println(stripeSecretKey);

        System.out.println(paymentDTO);
        long totalPrice = (long) paymentDTO.getAmount();
        String currency = paymentDTO.getCurrency();
        System.out.println(totalPrice);
        System.out.println(currency);

        // Dotenv dotenv = Dotenv.load();
        // Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");

        Stripe.apiKey = stripeSecretKey;
        try {
            PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
                    .setCurrency(currency)
                    .setAmount(totalPrice)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build())
                    .build();
            // System.out.println("[CHECKPOINT: SUCCESS]");
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            PaymentDTO response = new PaymentDTO();
            response.setClientSecret(paymentIntent.getClientSecret());
            return ResponseEntity.ok(response); // returns JSON object with client secret and http status code of 200
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            System.out.println("error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}