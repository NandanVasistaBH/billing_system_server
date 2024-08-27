package com.telstra.billing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.billing_system.model.Subscription;


public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

}
