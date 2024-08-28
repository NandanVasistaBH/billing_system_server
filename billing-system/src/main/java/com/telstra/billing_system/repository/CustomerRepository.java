package com.telstra.billing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.billing_system.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

