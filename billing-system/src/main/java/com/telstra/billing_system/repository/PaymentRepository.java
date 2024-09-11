package com.telstra.billing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telstra.billing_system.model.Payment;
@Repository("PaymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
