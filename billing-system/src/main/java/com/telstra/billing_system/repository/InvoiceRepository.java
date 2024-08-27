package com.telstra.billing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.billing_system.model.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
