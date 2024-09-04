package com.telstra.billing_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column(name = "payment_gateway", nullable = false)
    private PaymentGateway paymentGateway;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "amount_paid", nullable = false)
    private Double amountPaid;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @OneToMany(mappedBy = "payment")
    private Set<Invoice> invoices;
    
    public enum PaymentGateway {
        RazorPay
    }

}
