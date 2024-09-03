package com.telstra.billing_system.model;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "fk_supplier"))
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "subscription_id", foreignKey = @ForeignKey(name = "fk_subscription"))
    private Subscription subscription;

    private Double amountPaid;

    private Double tax;

    private LocalDate invoiceIssueDate;

    @ManyToOne
    @JoinColumn(name = "payment_id") // Foreign key column in the invoice table
    private Payment payment;
}
