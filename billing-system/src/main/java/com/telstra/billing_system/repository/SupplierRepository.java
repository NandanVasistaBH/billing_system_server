package com.telstra.billing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.billing_system.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
