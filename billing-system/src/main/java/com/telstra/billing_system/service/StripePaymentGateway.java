package com.telstra.billing_system.service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.exception.StripeException;
import com.telstra.billing_system.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentGateway {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public String processPayment(Payment payment) {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", payment.getAmountPaid().multiply(new BigDecimal(100)).intValue()); // Convert to cents
        chargeParams.put("currency", payment.getAmountPaid());
        chargeParams.put("source", payment.getPayId());

        try {
            Charge charge = Charge.create(chargeParams);
            return charge.getId(); // Return the transaction ID
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Stripe payment failed", e);
        }
    }
}
