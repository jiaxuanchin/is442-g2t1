package com.is442g2t1.ticketbookingsystem.stripe;

import lombok.Data;

@Data
public class PaymentDTO {
    private String clientSecret;
    private String publishableKey;
}
