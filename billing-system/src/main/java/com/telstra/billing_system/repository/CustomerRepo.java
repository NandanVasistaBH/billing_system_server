package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByName(String name);
}