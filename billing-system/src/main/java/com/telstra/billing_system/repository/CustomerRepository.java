package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.model.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Qualifier("CustomerRepository")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, User> {
    Customer findByName(String name);
}