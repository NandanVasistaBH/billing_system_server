package com.telstra.billing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.billing_system.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String>{

}


