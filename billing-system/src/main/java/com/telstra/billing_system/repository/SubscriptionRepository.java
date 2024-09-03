package com.telstra.billing_system.repository;
import com.telstra.billing_system.model.Subscription;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Qualifier("SubscriptionRepository")
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
