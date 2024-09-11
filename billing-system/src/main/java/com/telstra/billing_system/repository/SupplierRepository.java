package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Supplier;
import com.telstra.billing_system.model.User;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Qualifier("SupplierRepository")
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, User> {

    Supplier findByName(String username);
}
