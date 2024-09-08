package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("CustomerRepository")
public interface CustomerRepository extends JpaRepository<Customer, User> {
    Customer findByName(String name);
    Optional<List<Customer>> findBySupplierId(Integer supplierId);
}