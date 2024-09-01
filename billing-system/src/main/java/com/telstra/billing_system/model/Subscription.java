package com.telstra.billing_system.model;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Integer subscriptionId;

    @Column(name = "subscription_type", nullable = false)
    private String subscriptionType;

    @Column(name = "service_days", nullable = false)
    private Integer serviceDays;

    @Column(name = "no_of_days", nullable = false)
    private Integer noOfDays;

    @OneToMany(mappedBy = "subscription")
    private Set<Invoice> invoices;
}
