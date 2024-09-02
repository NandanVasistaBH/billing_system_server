package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    Admin findByName(String name);
}
