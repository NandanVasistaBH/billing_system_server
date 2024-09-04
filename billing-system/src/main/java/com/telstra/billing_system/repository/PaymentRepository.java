package com.telstra.billing_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.telstra.billing_system.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}