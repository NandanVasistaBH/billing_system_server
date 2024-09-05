package com.telstra.billing_system.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.telstra.billing_system.model.Invoice;
@Repository("InvoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByCustomerId(Integer customerId);
}
