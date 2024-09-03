package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {

    Supplier findByName(String username);
}
