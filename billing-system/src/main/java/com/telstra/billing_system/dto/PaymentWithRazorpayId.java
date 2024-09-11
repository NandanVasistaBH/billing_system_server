package com.telstra.billing_system.dto;

import com.telstra.billing_system.model.Payment;

import lombok.Data;
@Data
public class PaymentWithRazorpayId {
    Payment payment;
    String razorpayPaymentId;
}