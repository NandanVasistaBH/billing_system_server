package com.telstra.billing_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer"), nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "fk_supplier"), nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "subscription_id", foreignKey = @ForeignKey(name = "fk_subscription"), nullable = false)
    private Subscription subscription;

    private Double amountPaid;

    @Column(nullable = false)
    private Double tax;

    @Column(name = "invoice_issue_date", nullable = false, updatable = false)
    private LocalDate invoiceIssueDate;

    @Column(name = "last_updated_at", nullable = false, updatable = false)
    private LocalDateTime lastUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "payment_id") // Foreign key column in the invoice table
    private Payment payment;

    @PrePersist
    protected void onCreate() {
        if (invoiceIssueDate == null) {
            invoiceIssueDate = LocalDate.now();
        }
        lastUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }
}
