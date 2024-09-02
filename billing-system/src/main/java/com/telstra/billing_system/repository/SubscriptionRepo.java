package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Integer> {
}
