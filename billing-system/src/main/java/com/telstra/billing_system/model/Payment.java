package com.telstra.billing_system.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Integer payId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_gateway", nullable = false)
    private PaymentGateway paymentGateway;

    @Column(name = "PaymentMethod", nullable = false)
    private String PaymentMethod;

    @Column(name = "amount_paid", nullable = false, precision = 2)
    private Double amountPaid;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "transaction_date",nullable = false)
    private LocalDateTime transactionDate;

    public enum PaymentGateway {
        RazorPay
    }

}
