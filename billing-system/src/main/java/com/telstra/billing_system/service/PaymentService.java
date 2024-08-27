package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Payment;
import com.telstra.billing_system.model.Payment.PaymentGateway;
import com.telstra.billing_system.model.Payment.PaymentStatus;
import com.telstra.billing_system.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StripePaymentGateway stripePaymentGateway; // Example gateway service

    @Autowired
    private PayPalPaymentGateway payPalPaymentGateway; // Example gateway service

    @Transactional
    public Payment processPayment(Payment payment) {
        PaymentStatus status = PaymentStatus.FAILED;
        String transactionId = null;

        switch (payment.getPaymentGateway()) {
            case CREDIT_CARD:
            case DEBIT_CARD:
                // Logic for credit/debit card payments
                transactionId = stripePaymentGateway.processPayment(payment);
                status = PaymentStatus.COMPLETED;
                break;
            case PAYPAL:
                // Logic for PayPal payments
                transactionId = payPalPaymentGateway.processPayment(payment);
                status = PaymentStatus.COMPLETED;
                break;
            case BANK_TRANSFER:
                // Logic for bank transfers
                // Set status based on bank transfer processing
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment gateway: " + payment.getPaymentGateway());
        }

        payment.setStatus(status);
        payment.setTransactionId(transactionId);
        payment.setLastUpdate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}
