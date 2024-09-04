package com.telstra.billing_system.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.telstra.billing_system.model.Payment;

@Qualifier("PaymentRepository")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
