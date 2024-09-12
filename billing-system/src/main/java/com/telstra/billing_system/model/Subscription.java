package com.telstra.billing_system.model;
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

@Entity
@Table(name = "subscription")
@Data
@NoArgsConstructor
public class Subscription {

    public enum SubscriptionType {
        POSTPAID,
        PREPAID
    }
    public enum SubscriptionStatus{
        LIVE,
        PENDING_FOR_APPROVAL_LIVE,
        PENDING_FOR_APPROVAL_CLOSED,
        CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer noOfDays;

    @Column(nullable=false)
    private SubscriptionStatus status;
}
